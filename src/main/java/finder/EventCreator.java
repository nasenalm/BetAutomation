package finder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class EventCreator {

    ArrayList<Event> events = new ArrayList<>();


    // creates a parser object using the findProfit.JsonParser class.

    JSONObject jo = new JSONObject(); //events


    public void createEvents(JSONArray joA) { //take parameter (either pinnacle or general json array object)

        for (int o = 0; o < joA.size(); o++) {
            jo = (JSONObject) joA.get(o); // widest scope each jo is an event
            String id = ((String) jo.get("id"));
            String sportKey = ((String) jo.get("sport_key"));
            String sportTitle = ((String) jo.get("sport_title"));
            String commenceTime = ((String) jo.get("commence_time"));
            String homeTeam = ((String) jo.get("home_team"));
            String awayTeam = ((String) jo.get("away_team"));

            JSONArray bookieList = ((JSONArray) jo.get("bookmakers"));
            System.out.println("Number of Bookmakers (per event): " + bookieList.size());
            if (bookieList.size()==0){return;}

            events.add(new Event(id, sportKey, sportTitle, commenceTime, homeTeam, awayTeam));

            for (int i = 0; i< bookieList.size(); i++){
                JSONObject bookieDetails; // array within an event that holds bookies (not used until nested loop)
                bookieDetails = (JSONObject) bookieList.get(i);
                String bookieKey = ((String) bookieDetails.get("key"));
                String bookieName = ((String) bookieDetails.get("name"));
                String lastUpdate = ((String) bookieDetails.get("last_update"));

                events.get(o).addBookie(new Bookie(bookieKey, bookieName, lastUpdate));

                //create a json array of markets to be looped through in a third nested loop of markets
                JSONArray marketList = ((JSONArray) (bookieDetails.get("markets")));
                for (Object value : marketList) {
                    JSONObject mark;
                    mark = (JSONObject) value;
                    String market1 = ((String) mark.get("key"));
                    String team1 = "";
                    double odds1 = 0;
                    String team2 = "";
                    double odds2 = 0;
                    double oddsdraw = 0;
                    double totalsPoints = 0;

                    JSONArray outcomes = ((JSONArray) mark.get("outcomes"));
                    for (int out = 0; out < outcomes.size(); out++) {
                        JSONObject outcome;
                        outcome = (JSONObject) outcomes.get(out);
                        if (out == 0) {
                            team1 = ((String) outcome.get("name"));
                            odds1 = ((Double) outcome.get("price"));
                            try {
                                totalsPoints = ((Double) outcome.get("point"));
                            } catch (Exception e) {
                                //throw new RuntimeException(e);
                            }


                        } else if (out == 1) {
                            team2 = ((String) outcome.get("name"));
                            odds2 = ((Double) outcome.get("price"));
                            try {
                                totalsPoints = ((Double) outcome.get("point"));
                            } catch (Exception e) {
                                //
                            }


                        } else if (out == 2) {
                            oddsdraw = ((Double) outcome.get("price"));
                        }
                    }

                    //create a bet object and add it to bets arraylist that is in a bookie object
                    Bet betski = new Bet(market1, team1, team2, odds1, odds2, oddsdraw, totalsPoints, bookieKey);
                    events.get(o).getBookies().get(i).addBet(betski);

                }

            }

        }
    }
    public ArrayList<Event> getEvents() {
        return events;
    }


}