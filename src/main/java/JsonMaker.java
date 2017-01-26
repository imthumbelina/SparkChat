import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by aga on 26.01.17.
 */
public class JsonMaker {

    private final String url="http://api.openweathermap.org/data/2.5/weather?q=Cracow&units=metric&APPID=f5987173cc795b81268b1e1ccb43deb1";
    public String getWeather() throws IOException {

        String temperature="";
        String humidity ="";
        String pressure="";
        String result="";
        try {
            String readjson = readUrl();
            JSONObject newjson = new JSONObject(readjson);
            temperature = "Pogoda w Krakowie.Temperatura to"+newjson.getJSONObject("main").getString("temp")+"stopni Celsjusza.";
            humidity = "Wilgotność to"+ newjson.getJSONObject("main").getString("humidity")+".";
            pressure="Cisnienie to "+ newjson.getJSONObject("main").getString("pressure")+" hektopaskali.";

            result=temperature+humidity+pressure;


        }
        catch(JSONException e){
            System.out.print("");
        }
        return result;


    }

    private String readUrl() throws IOException{
        URL url = new URL(this.url);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuilder builder = new StringBuilder();
        while((input = in.readLine()) != null){
            builder.append(input);
        }
        in.close();
        return String.valueOf(builder);


    }
}
