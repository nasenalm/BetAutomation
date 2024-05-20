package finder;

public class Bet {

    // this is the most specific class I will have

    String bookMaker;
    String market; // what type of bet (h2h, h2h_lay, spread, over/under, etc.)
    String team1; // team1 will not always be the home or away team as listen in findProfit.Event
    String team2; // additionally some sports may not have home teams listed so this could be a better place for teams!
    double odds1; // stored as decimal odds
    double odds2; // stored as decimal odds
    double totalPoint; // the point value for over under of total game
    double oddsDraw; // stored as decimal odds
    double fairOdds1; // stored as decimal
    double fairOdds2; // stored as decimal
    double fairOddsDraw; // stored as decimal
    double evPercent1;
    double evPercent2;
    double evPercentDraw;




    public Bet(String mark, String t1, String t2, double o1, double o2, double draw, double total, String bookie){
        bookMaker = bookie;
        market=mark;
        team1=t1;
        team2=t2;
        odds1=o1;
        odds2=o2;
        oddsDraw = draw;
        totalPoint=total;


        //remove bookmaker vig
        //ODDS DRAW IS OFTEN 0 MAKING THIS INFINITY
        if(draw != 0){
            fairOdds1 = 1/((1/o1)/ ((1/o1)+(1/o2)+(1/draw))); // 1/ percent of event happening/ total percent
            fairOdds2 = 1/((1/o2)/ ((1/o1)+(1/o2)+(1/draw)));
            fairOddsDraw = 1/((1/draw)/ ((1/o1)+(1/o2)+(1/draw)));

            evPercent1 = 100*(1/fairOdds1 - 1/o1);
            evPercent2 = 100*(1/fairOdds2 - 1/o2);
            evPercentDraw = 100*(1/fairOddsDraw - 1/draw);
        } else{
            fairOdds1 = 1/((1/o1)/ ((1/o1)+(1/o2)));
            fairOdds2 = 1/((1/o2)/ ((1/o1)+(1/o2)));
            evPercent1 = 100*(1/fairOdds1 - 1/o1);
            evPercent2 = 100*(1/fairOdds2 - 1/o2);
        }


    }


    public double getOdds1(){return odds1;}
    public double getOdds2(){return odds2;}
    public String getMarket(){return market;}
    public String getTeam1(){return team1;}
    public String getTeam2(){return team2;}
    public double getTotalPoint() {return totalPoint;}
    public double getFairOdds1() {return fairOdds1;}
    public double getFairOdds2() {return fairOdds2;}
    public double getFairOddsDraw() {return fairOddsDraw;}
    public String getBookMaker() {return bookMaker;}
    public double getOddsDraw() {return oddsDraw;}


}