package pt.ua.salome.tqs_recurso;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("moedas")
@RequestScoped
public class ConversaoResource {

   @EJB
   ConversaoPagina conversao;
   @EJB
   APIConversao api;
   
   @GET
   @Produces({ "application/xml", "application/json" })
   public String listarMoedas() {
       String resultado = "";
       for(String moeda: conversao.getListaMoedas()){
           resultado = resultado + "/n" + moeda;
       }
      return resultado;
   }
   
   @GET
   @Produces({ "application/json", "application/xml" })
   @Path("{valor}:{moeda1}to{moeda2}")
   public String calculate(@PathParam("valor") String valor, 
           @PathParam("moeda1") String moeda1, @PathParam("moeda2") String moeda2)
   {
      return api.calcularConversao(valor, moeda1, moeda2);
   }
}
