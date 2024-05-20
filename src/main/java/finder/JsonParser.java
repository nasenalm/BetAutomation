package finder;// Java program to read JSON from a file

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {

    JSONArray joA;
    JSONArray joAPinnacle;
    public JsonParser(){

    //CREATE joA (NOT PINNACLE
    HttpGet theOdds = new HttpGet(new UrlCreator("the-odds-api key here")); // enter your api key
    String json = theOdds.getJsonString();

    // parsing file "JSONExample.json"
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        joA = (JSONArray) obj;
    }

    public JSONArray getJoA(){
        return joA;
    }


}



