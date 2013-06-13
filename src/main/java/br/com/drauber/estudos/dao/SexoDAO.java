package br.com.drauber.estudos.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.drauber.estudos.pojo.Sexo;
import br.com.drauber.estudos.util.HibernateUtil;
import static br.com.drauber.estudos.util.HibernateUtil.getSession;
import javax.faces.application.FacesMessage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SexoDAO {

    SessionFactory sf = HibernateUtil.getSessionFactory();
    Session s;

    public SexoDAO() {
    }

    public FacesMessage saveOrUpdate(Sexo sexo) {
        try {
            s = sf.openSession();
            if (sexo.getId() != null) {
                Object pDuplica = s.get(sexo.getClass(), sexo.getId());
                if (pDuplica != null) {
                    s.evict(pDuplica);
                }
            }
            s.beginTransaction();
            s.saveOrUpdate(sexo);
            s.getTransaction().commit();
            return new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso!", "");
        } catch (HibernateException e) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problema ao gravar!", e.getMessage());
        } finally {
            s.close();
        }
    }

    public FacesMessage delete(Sexo sexo) {
        try {
            s = sf.openSession();
            Object pDuplica = s.get(sexo.getClass(), sexo.getId());
            if (pDuplica != null) {
                s.evict(pDuplica);
            }
            s.beginTransaction();
            s.delete(sexo);
            s.getTransaction().commit();
            return new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", "");
        } catch (HibernateException e) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problema ao excluir!", e.getMessage());
        } finally {
            s.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Sexo> getAll() {
        s = sf.openSession();
        Criteria criteria = s.createCriteria(Sexo.class);
        List<Sexo> l = (List<Sexo>) criteria.addOrder(Order.asc("id")).list();
        s.close();
        return l;
        
    }

    //Mais sobre criteria em: http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/querycriteria.html
    @SuppressWarnings("unchecked")
    public List<Sexo> pesquisar(String campo, Object valor) {
        s = sf.openSession();
        Criteria criteria = s.createCriteria(Sexo.class);
        if (campo.equals("id")) { //pesquisa por id
            int i = Integer.valueOf(valor.toString().trim()); //remove os espaços antes de converter
            criteria.add(Restrictions.eq(campo, i));
        } else { //pesquisa por nome
            criteria.add(Restrictions.like(campo, "%" + valor + "%"));
        }
        List<Sexo> l = (List<Sexo>) criteria.addOrder(Order.asc("id")).list();
        s.close();
        return l;
    }
}