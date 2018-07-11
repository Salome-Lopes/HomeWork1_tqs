package pt.ua.salome.tqs_recurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "conversaoPagina", eager = true)
@SessionScoped
@Singleton
public class ConversaoPagina implements Serializable{
    
   private String deMoeda;
   private String deValor;
   private String paraMoeda;
   private String mensagemErro;
   private String resultado;
   private List<String> listaMoedas = new ArrayList<>();
   
   private APIConversao api;
   
   @PostConstruct
   public void construct(){
       resultado = " ";
       mensagemErro = " ";
       listaMoedas = new ArrayList<>();
       api = new APIConversao();
   }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public String getDeValor() {
        return deValor;
    }
   
    public void setDeValor(String deValor) {
        this.deValor = deValor;
    }
   
    public String getDeMoeda() {
        return deMoeda;
    }

    public void setDeMoeda(String deMoeda) {
        this.deMoeda = deMoeda;
    }

    public String getParaMoeda() {
        return paraMoeda;
    }

    public void setParaMoeda(String paraMoeda) {
        this.paraMoeda = paraMoeda;
    }

   public void atualizarMoedas(){
        listaMoedas = api.getMoedas();
   }
   
   public List<String> getListaMoedas() {
      if(listaMoedas.isEmpty()) 
          atualizarMoedas();
      return listaMoedas;
   }
   
    public String getResultado() {
        return resultado;
    }
   
   public String buttonAction() {
       if(!deValor.matches("[0-9]+(\\.[0-9]+)?")){
           this.mensagemErro = "Por favor insire um número.";
           resultado = "ERRO!";
       }
       else{
           this.mensagemErro = "";
           resultado = api.calcularConversao(deValor,deMoeda,paraMoeda);
        
       }
        return "home.xhtml";
    }


   
}