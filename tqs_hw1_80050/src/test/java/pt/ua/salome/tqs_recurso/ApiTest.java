package pt.ua.salome.tqs_recurso;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class ApiTest {
    
    public ApiTest() {
    }

    @Test
    public void testeGetMoedas() {
        APIConversao api = new APIConversao();
        List<String> resultado = api.getMoedas();
        assertTrue("Não vazia",!resultado.isEmpty());
    }


    @Test
    public void testeCalculoTaxa() {
        APIConversao api = new APIConversao();
        String resultado = api.calcularConversao("1", "USD", "EUR");
        assertTrue(Double.parseDouble(resultado) > 0);
    }
}
