package finder;

public class UrlCreator {

    // Set up the URL and API key


    String apiKey = ""; // odds api key.
    String region; // us, au, uk, eu (multiple regions can be specified using commas)
    String sport; //can use "upcoming" to get data for next 8 games in all sports
    String market; // h2h (head to head / moneyline), spreads, totals (over/under), outrights (future). defaults to h2h
    String urlString;

    public UrlCreator(String key){ // most basic constructor next 8 games all sports in all regions
        urlString = "https://api.the-odds-api.com/v4/sports/upcoming/odds/?apiKey=" + key + "&regions=us,eu,au,uk"+"&markets=spreads"; //I only have accounts with us bookies
    }

    public UrlCreator(String key, String sp, String mark, String reg){
        apiKey = key;
        region = reg;
        sport = sp;
        market = mark;
        urlString = "https://api.the-odds-api.com/v4/sports/" + sport + "/odds/?apiKey=" + apiKey + "&regions=" + region + "&markets=" + market;

    }

    public String getUrlString() {
        return urlString;
    }
}
