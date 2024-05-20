package finder;

import java.util.ArrayList;

public class Event {

    String eventID; // "id"
    String sportKey; //slightly more broad than title "sport_key"
    String sportTitle; //league "sport_title"
    String commenceTime; // "commence_time"
    String homeTeam; // "home_team"
    String awayTeam; // "away_team"

    ArrayList<Bookie> bookies = new ArrayList<>();

    public Event(String id, String sportK, String sportT, String commence, String home, String away){
        eventID = id;
        sportKey = sportK;
        sportTitle = sportT;
        commenceTime = commence;
        homeTeam = home;
        awayTeam = away;
    }

    public void addBookie(Bookie b){
        bookies.add(b);
    }
    public ArrayList<Bookie> getBookies(){
        return bookies;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }
}
