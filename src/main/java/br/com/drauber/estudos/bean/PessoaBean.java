package br.com.drauber.estudos.bean;

import br.com.drauber.estudos.dao.PessoaDAO;
import br.com.drauber.estudos.pojo.Pessoa;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@RequestScoped
public class PessoaBean {

    //Propriedades
    private Pessoa pessoaSelecionado = new Pessoa();
    private PessoaDAO pessoaDAO = new PessoaDAO();
    private List<Pessoa> lista = null;
    private String campoFiltro = "DESCRICAO";
    private String valorFiltro = "";
    //Constantes
    private final String PAGINA_LISTAGEM = "auth/pessoas.xhtml";
    private final String PAGINA_EDICAO = "auth/pessoas.xhtml";

    //Contrutor
    public PessoaBean() {
        filtrar();
    }

    //Ajax da View
    public void onRowSelect(SelectEvent event) {
        setPessoaSelecionado((Pessoa) event.getObject());
    }

    // Métodos
    public String salvar() {
        System.out.println("chamou salvar de PessoaBean");
        if (pessoaSelecionado.getId() == 0) {
            pessoaSelecionado.setId(null);
        }
        FacesMessage m = pessoaDAO.saveOrUpdate(pessoaSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, m);
        inserir();
        filtrar();
        return PAGINA_LISTAGEM;
    }

    public String inserir() {
        pessoaSelecionado = new Pessoa();
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
        System.out.println("chamou deletar() de PessoaBean");
        FacesMessage m = pessoaDAO.delete(pessoaSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, m);
        lista.remove(pessoaSelecionado);
        pessoaSelecionado = new Pessoa();
        return PAGINA_LISTAGEM;
    }

    public String filtrar() {
        if (valorFiltro.trim().equalsIgnoreCase("")) { //pesquisa em branco, retorna todos registros
            lista = pessoaDAO.getAll();
        } else {
            lista = pessoaDAO.pesquisar(campoFiltro, valorFiltro);
        }
        return PAGINA_LISTAGEM;
    }

    public Pessoa getPessoaSelecionado() {
        return pessoaSelecionado;
    }

    public void setPessoaSelecionado(Pessoa pessoaSelecionado) {
        System.out.println("setPessoaSelecionado");
        this.pessoaSelecionado = pessoaSelecionado;
    }

    public PessoaDAO getPessoaDAO() {
        return pessoaDAO;
    }

    public void setPessoaDAO(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public List<Pessoa> getLista() {
        return lista;
    }

    public void setLista(List<Pessoa> lista) {
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
