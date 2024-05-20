package finder;

public class EvPresent {

    private Long id;
    private double odds;
    private double fairOdds;
    private double evPercent;
    private String market; //h2h, spreads, totals etc.
    private String bookie; // bookmaker name
    private String teamWin; // will be in the format of (Team1 vs. Team2)
    private String teamLose; // will be either a team name or draw
    private double line;
    private String betOn;


    public EvPresent(){

    }

    public EvPresent(double odds, double fairOdds, double evPercent, String market, String bookie, String teamWin, String teamLose, double line, String betOn){
        this.odds = odds;
        this.fairOdds = (double) Math.round(fairOdds * 100) / 100;
        this.evPercent = (double) Math.round(evPercent * 100) / 100;
        this.market = market;
        this.bookie = bookie;
        this.teamWin = teamWin;
        this.teamLose = teamLose;
        this.line = line;
        this.betOn = betOn;
    }

    public final double getEvPercent() {
        return evPercent;
    }

    public final double getFairOdds() {
        return fairOdds;
    }

    public final double getOdds() {
        return odds;
    }

    public final Long getId() {
        return id;
    }

    public final String getTeamWin() {
        return teamWin;
    }

    public final String getBookie() {
        return bookie;
    }

    public final String getMarket() {
        return market;
    }

    public final String getTeamLose() {
        return teamLose;
    }
    public final String getBetOn(){return betOn;}


public final double getLine(){return line;}
}
