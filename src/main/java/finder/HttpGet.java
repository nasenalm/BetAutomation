package finder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGet {

    String jsonString = "";

    public HttpGet(UrlCreator urlStr) {
        try {

        //create URL using findProfit.UrlCreator class
        String urlString = urlStr.getUrlString();


        // Create a URL object and open a connection

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Get the response code and response body
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print out the response code and response body
        System.out.println("Response Code: " + responseCode); // put this in a public function then return json string!!
        jsonString = response.toString();
    }catch (Exception e) {
            System.out.println("An error occurred: " + e);

        }
    }

    public String getJsonString(){
        return jsonString;
    }

}
