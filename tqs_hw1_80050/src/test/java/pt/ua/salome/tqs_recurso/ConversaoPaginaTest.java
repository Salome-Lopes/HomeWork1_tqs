package pt.ua.salome.tqs_recurso;

import java.util.ArrayList;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;


@RunWith(EasyMockRunner.class)
public class ConversaoPaginaTest {

    
    @Mock
    APIConversao api;
    
    @Test
    public void testeValoresVazios() {
        ConversaoPagina conversaoPagina = new ConversaoPagina();
        conversaoPagina.setDeValor("");
        conversaoPagina.buttonAction();
        assertEquals("Test - Value is blank",conversaoPagina.getResultado(), "ERRO!");
    }
    

    @Test
    public void testeValoresNaoVazios() {
        ConversaoPagina conversao = new ConversaoPagina();
        conversao.setDeValor("a");
        conversao.buttonAction();
        assertEquals("Test - Value is 'a'",conversao.getResultado(), "ERRO!");
    }

    @TestSubject
    ConversaoPagina conversaoP = new ConversaoPagina();
   

    @Test
    public void testeConversao(){
        EasyMock.expect(api.calcularConversao("1", "USD","USD")).andReturn("1.0");
        EasyMock.replay(api);
        
        conversaoP.setDeMoeda("USD");
        conversaoP.setParaMoeda("USD");
        conversaoP.setDeValor("1");
        conversaoP.buttonAction();
        
        assertEquals("Resultado dá 1.0",conversaoP.getResultado(), "1.0");
    }
    
    @Test
    public void testeCache(){
        ArrayList<String> mockOutput = new ArrayList<>();
        mockOutput.add("");
        
        EasyMock.expect(api.getMoedas()).andReturn(mockOutput);
        EasyMock.replay(api);
        
        assertEquals("Resultado tem que ser igual nos dois",conversaoP.getListaMoedas(), mockOutput);
    }

}
