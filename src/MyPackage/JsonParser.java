package MyPackage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

/**
 * Created by jagesh on 05/29/2016.
 */
public class JsonParser
{
    private final JSONObject jsonObject;
    public String htmlstring = "http://api.openweathermap.org/data/2.5/weather?q=Beijing&mode=html&appid=6df9e79cb545be4423d988107b873cfe";

    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException
    {

//        URL api = new URL("http://api.openweathermap.org/data/2.5/weather?id=1816670&appid=69600db5f74dfdd3f54990f73cbad911");
//        URLConnection connection = api.openConnection();
//        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//        JSONObject jsonObject = (JSONObject) new JSONParser().parse(br);
//        br.close();
//
//        //JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
//
//        JSONObject main = (JSONObject) jsonObject.get("main");
//        Double tempmin = (Double) main.get("temp_min");
//        String city = (String ) jsonObject.get("name");
//        System.out.print(city);
//        JSONObject wind = (JSONObject) jsonObject.get("wind");
//        Long speed = (Long) wind.get("speed");
//        System.out.print(speed);
//        //String weather = jsonObject.get("main").toString();
//
//      System.out.println(tempmin);
    }


    public JsonParser() throws IOException, org.json.simple.parser.ParseException
    {
        URL api = new URL("http://api.openweathermap.org/data/2.5/weather?id=1816670&appid=69600db5f74dfdd3f54990f73cbad911");
        URLConnection connection = api.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        jsonObject = (JSONObject) new JSONParser().parse(br);
        br.close();
    }

    //HTML Parsaing
    public String getHtmlapi() throws IOException
    {
        URL htmlstr = new URL(htmlstring);
        URLConnection connection = htmlstr.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String htmlapi;
        StringBuilder weatherHtml = new StringBuilder();
        while ((htmlapi = br.readLine()) != null)
        {
            weatherHtml.append(htmlapi) ;
        }

        return weatherHtml.toString();
    }

    public String getCity(){
        String city = (String) jsonObject.get("name");

        return city;
    }

    public Double getMaxtemp()
    {
        JSONObject main = (JSONObject) jsonObject.get("main");
        Double maxtemp = (Double) main.get("temp_max");

        return maxtemp;
    }

    public Double getMinTemp(){
        JSONObject main = (JSONObject) jsonObject.get("main");
        Double mintemp = (Double) main.get("temp_min") ;

        return mintemp;
    }

    public Long getSpeed()
    {
        JSONObject wind = (JSONObject)jsonObject.get("wind");

        return ((Long) wind.get("speed"));
    }
}
