
package pt.ua.salome.tqs_recurso;

import java.io.File;
import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RestApiTest {
    
        private WebTarget teste1;
    private WebTarget teste2;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve()
                .withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(
                        ConversaoApplication.class, ConversaoResource.class,
                        APIConversao.class, ConversaoPagina.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")
                .addAsLibraries(files);
    }
    
    @ArquillianResource
    private URL base;


    @Before
    public void setUp() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        teste1 = client.target(URI.create(new URL(base, "REST/moedas").toExternalForm()));
        teste1.register(APIConversao.class);
        teste2 = client.target(URI.create(new URL(base, "REST/moedas/1:USDtoUSD").toExternalForm()));
        teste2.register(APIConversao.class);
    }
    
    @Test @InSequence(1)
    public void testeListarMoedas() {
        Response resposta = teste1.request().get();
        resposta.bufferEntity();
        String s = resposta.readEntity(String.class);
        assertTrue(s.length() > 0);
        resposta.close();
        
    }
    @Test @InSequence(2)
    public void testeCalcularValor() {
        Response resposta = teste2.request().get();
        resposta.bufferEntity();
        String s = resposta.readEntity(String.class);
        assertTrue(s.equals("1.0"));
        resposta.close();
    }
}
