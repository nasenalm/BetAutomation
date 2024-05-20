package finder;

import java.util.ArrayList;

public class Bookie {

    String bookieKey;
    String bookieTitle;
    String lastUpdate; //should probably change this to a time object (maybe)
    ArrayList<Bet> bets = new ArrayList<>();

    public Bookie(String key, String title, String updated){
        bookieKey = key;
        bookieTitle = title;
        lastUpdate = updated;
    }

    public void addBet(Bet b){
        bets.add(b);
    }
    public ArrayList<Bet> getBets(){
        return bets;
    }
    public String getBookieKey(){
        return bookieKey;
    }

    public String getBookieTitle(){
        return bookieTitle;
    }

}
