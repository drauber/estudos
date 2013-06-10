package br.com.drauber.estudos.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.drauber.estudos.pojo.Sexo;
import static br.com.drauber.estudos.util.HibernateUtil.getSession;

public class SexoDAO {

	public SexoDAO(){}
	
	public void saveOrUpdate(Sexo sexo) {
		getSession().saveOrUpdate(sexo);
	}
		
	public void delete(Sexo sexo){
		getSession().delete(sexo);	
	}
	
	@SuppressWarnings("unchecked")
	public List<Sexo> getAll(){
		Criteria criteria = getSession().createCriteria(Sexo.class);
	 	return  (List<Sexo>) criteria.addOrder(Order.asc("id")).list();
	}

	//Mais sobre criteria em: http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/querycriteria.html
	@SuppressWarnings("unchecked")
	public List<Sexo> pesquisar(String campo, Object valor){
		Criteria criteria = getSession().createCriteria(Sexo.class);
		if(campo.equals("id")){ //pesquisa por id
			int i = Integer.valueOf(valor.toString().trim()); //remove os espaços antes de converter
			criteria.add(Restrictions.eq(campo, i));
		}else{ //pesquisa por nome
			criteria.add(Restrictions.like(campo, "%" + valor + "%"));
		}			
	 	return  (List<Sexo>) criteria.addOrder(Order.asc("id")).list();
	}
}