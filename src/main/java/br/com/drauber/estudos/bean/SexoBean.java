package br.com.drauber.estudos.bean;

import br.com.drauber.estudos.dao.SexoDAO;
import br.com.drauber.estudos.pojo.Sexo;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@RequestScoped
public class SexoBean {

    //Propriedades
    private Sexo sexoSelecionado = new Sexo();
    private SexoDAO sexoDAO = new SexoDAO();
    private List<Sexo> lista = null;
    private String campoFiltro = "DESCRICAO";
    private String valorFiltro = "";
    //Constantes
    private final String PAGINA_LISTAGEM = "auth/sexos.xhtml";
    private final String PAGINA_EDICAO = "auth/sexos.xhtml";

    //Contrutor
    public SexoBean() {
        filtrar();
    }

    //Ajax da View
    public void onRowSelect(SelectEvent event) {
        setSexoSelecionado((Sexo) event.getObject());
    }

    // Métodos
    public String salvar() {
        if (sexoSelecionado.getId() == 0) {
            sexoSelecionado.setId(null);
        }
        FacesMessage m = sexoDAO.saveOrUpdate(sexoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, m);
        inserir();
        filtrar();
        return PAGINA_LISTAGEM;
    }

    public String inserir() {
        sexoSelecionado = new Sexo();
        return PAGINA_EDICAO;
    }

    public String editar() {
        return PAGINA_EDICAO;
    }

    public String cancelar() {
        filtrar();
        inserir();
        return PAGINA_LISTAGEM;
    }

    public String deletar() {
        FacesMessage m = sexoDAO.delete(sexoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, m);
        lista.remove(sexoSelecionado);
        sexoSelecionado = new Sexo();
        return PAGINA_LISTAGEM;
    }

    public String filtrar() {
        if (valorFiltro.trim().equalsIgnoreCase("")) { //pesquisa em branco, retorna todos registros
            lista = sexoDAO.getAll();
        } else {
            lista = sexoDAO.pesquisar(campoFiltro, valorFiltro);
        }
        return PAGINA_LISTAGEM;
    }

    public Sexo getSexoSelecionado() {
        return sexoSelecionado;
    }

    public void setSexoSelecionado(Sexo sexoSelecionado) {
        System.out.println("setSexoSelecionado");
        this.sexoSelecionado = sexoSelecionado;
    }

    public SexoDAO getSexoDAO() {
        return sexoDAO;
    }

    public void setSexoDAO(SexoDAO sexoDAO) {
        this.sexoDAO = sexoDAO;
    }

    public List<Sexo> getLista() {
        return lista;
    }

    public void setLista(List<Sexo> lista) {
        this.lista = lista;
    }

    public String getCampoFiltro() {
        return campoFiltro;
    }

    public void setCampoFiltro(String campoFiltro) {
        this.campoFiltro = campoFiltro;
    }

    public String getValorFiltro() {
        return valorFiltro;
    }

    public void setValorFiltro(String valorFiltro) {
        this.valorFiltro = valorFiltro;
    }
}
