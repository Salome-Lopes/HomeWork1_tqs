package pt.ua.salome.tqs_recurso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import javax.ejb.Singleton;

@Singleton
public class APIConversao{

    public static final String URL = "http://apilayer.net/api/";
    public static final String CHAVE_API = "856811d0ca6536bc9c28b0a187717bf8";
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    
    
    public List<String> getMoedas(){
        
        HttpGet get = new HttpGet(URL + "list" + "?access_key=" + CHAVE_API);

        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
            Iterator<String> it = exchangeRates.getJSONObject("currencies").keys();
            List<String> lista = new ArrayList<>();
            while(it.hasNext())
                lista.add(it.next());
            response.close();
            return lista; 
        } catch (Exception e) {System.out.println(e);}
        return null;
    }
    
    

    public String calcularConversao(String fromValue, String fromCurrency, String toCurrency) {
        HttpGet get = new HttpGet(URL + "live" + ""
                + "?access_key=" + CHAVE_API + 
                "&currencies=" + fromCurrency + "," + toCurrency + "&format=1");
        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject taxas = new JSONObject(EntityUtils.toString(entity));
            JSONObject json = taxas.getJSONObject("quotes");
            double valor1 = json.getDouble("USD" + fromCurrency);
            double valor2 = json.getDouble("USD" + toCurrency);
            return String.valueOf(valor2*Double.parseDouble(fromValue)/valor1);
            
        } catch (Exception e) {
            System.out.println(e);} 
        return "Erro";
    }

}
