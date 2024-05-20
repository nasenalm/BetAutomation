package finder;

import java.util.ArrayList;

public class CheckArbitrage {

    //instance data
    ArrayList<ArbitragePresent> arbPairs = new ArrayList<>();

    //
    //constructor
    //
    public CheckArbitrage(ArrayList<Event> events,int stakePerBet) { // change params

        for (Event e : events) {

            for (int bookieIndex1 = 0; bookieIndex1 < e.getBookies().size(); bookieIndex1++) {

                for (int bookieIndex2 = 0; bookieIndex2 < e.getBookies().size(); bookieIndex2++) {

                    Bookie b1 = e.getBookies().get(bookieIndex1);
                    Bookie b2 = e.getBookies().get(bookieIndex2);

                    if (!(b1.getBookieKey().equals(b2.getBookieKey()))) {

                        for (int i = 0; i < b1.getBets().size(); i++) { 
                            for (int j = 0; j < b2.getBets().size(); j++) {

                                Bet bet1 = b1.getBets().get(i);
                                Bet bet2 = b2.getBets().get(j);

                                if (bet1.getMarket().equals(bet2.getMarket())) {

                                    //This asks if the event can draw
                                    if (bet1.oddsDraw == 0 & bet2.oddsDraw == 0) {
                                        //REPLACE WITH A SWITCH STATEMENT IF POSSIBLE
                                        if ((bet1.market.equals("totals") && bet1.totalPoint == bet2.totalPoint) || (bet1.market.equals("h2h")) || bet1.market.equals("h2h_lay")) {
                                            if (1 / bet1.getOdds1() + 1 / bet2.getOdds2() < 1.0) {


                                                double profit = 100 - (stakePerBet / bet1.getOdds1() + stakePerBet / bet2.getOdds2());
                                                System.out.println("\nfindProfit.Bookie 1: " + b1.getBookieKey() + ", findProfit.Bookie 2 " + b2.getBookieKey());
                                                System.out.println("" + bet1.getMarket());
                                                System.out.println("Home team: " + e.homeTeam + ", Away team: " + e.awayTeam);
                                                System.out.println("findProfit.Bet: " + stakePerBet / bet1.getOdds1() + " on outcome: " + bet1.getTeam1() + " on bookmaker: " + b1.getBookieKey());
                                                System.out.println("findProfit.Bet: " + stakePerBet / bet2.getOdds2() + " on outcome: " + bet2.getTeam2() + " on bookmaker: " + b2.getBookieKey());
                                                System.out.println("Percentage profit: " + profit + "\n");

                                                arbPairs.add(new ArbitragePresent(bet1, bet2, true, profit)); // this creates a list of bet pairs that have arbitrage

                                            } else if (1 / bet1.getOdds2() + 1 / bet2.getOdds1() < 1.0) {

                                                System.out.println("" + bet1.getTotalPoint() + bet2.getTotalPoint());

                                                double profit = 100 - (stakePerBet / bet1.getOdds2() + stakePerBet / bet2.getOdds1());
                                                System.out.println("\nfindProfit.Bookie 1: " + b1.getBookieKey() + ", findProfit.Bookie 2 " + b2.getBookieKey());
                                                System.out.println("" + bet1.getMarket());
                                                System.out.println("Home team: " + e.homeTeam + ", Away team: " + e.awayTeam);
                                                System.out.println("findProfit.Bet: " + stakePerBet / bet1.getOdds2() + " on outcome: " + bet1.getTeam2() + " on bookmaker: " + b1.getBookieKey());
                                                System.out.println("findProfit.Bet: " + stakePerBet / bet2.getOdds1() + " on outcome: " + bet2.getTeam1() + " on bookmaker: " + b2.getBookieKey());
                                                System.out.println("Percentage profit: " + profit + "\n");
                                                arbPairs.add(new ArbitragePresent(bet1, bet2, false, profit)); // this creates a list of bet pairs that have arbitrage

                                            }
                                        }

                                    } else {
                                        if ((1 / bet1.odds1 + 1 / bet2.odds2 + 1 / bet1.oddsDraw) < 1.0) {

                                            double profit = 100 - (stakePerBet / bet1.getOdds1() + stakePerBet / bet2.getOdds2() + stakePerBet / bet1.oddsDraw);
                                            System.out.println("\nHome team: " + e.homeTeam + ", Away team: " + e.awayTeam);
                                            System.out.println("findProfit.Bookie 1: " + b1.getBookieKey() + ", findProfit.Bookie 2 " + b2.getBookieKey());
                                            System.out.println("Market: " + bet1.getMarket() + "\nTeam 1: " + bet1.getTeam1() + ", Team 2: " + bet2.getTeam2());
                                            System.out.println("Suggested bet on Team 1: " + stakePerBet / bet1.getOdds1() + ", Odds 1: " + bet1.getOdds1());
                                            System.out.println("Suggested bet on Team 2: " + stakePerBet / bet2.getOdds2() + ", Odds 2: " + bet2.getOdds2());
                                            System.out.println("Suggested bet for Draw: " + stakePerBet / bet1.oddsDraw);
                                            System.out.println("findProfit.Bet draw on: " + b1.getBookieKey() + "\n");
                                            arbPairs.add(new ArbitragePresent(bet1, bet2, true, profit)); // this creates a list of bet pairs that have arbitrage

                                        } else if ((1 / bet1.odds2 + 1 / bet2.odds1 + 1 / bet1.oddsDraw) < 1.0) {

                                            double profit = 100 - (stakePerBet / bet1.getOdds2() + stakePerBet / bet2.getOdds1() + stakePerBet / bet1.oddsDraw);
                                            System.out.println("\nHome team: " + e.homeTeam + ", Away team: " + e.awayTeam);
                                            System.out.println("findProfit.Bookie 1: " + b1.getBookieKey() + ", findProfit.Bookie 2 " + b2.getBookieKey());
                                            System.out.println("Market: " + bet1.getMarket() + "\nTeam 1: " + bet1.getTeam2() + ", Team 2: " + bet2.getTeam1());
                                            System.out.println("Suggested bet on Team 1: " + stakePerBet / bet1.getOdds2() + ", Odds 1: " + bet1.getOdds2());
                                            System.out.println("Suggested bet on Team 2: " + stakePerBet / bet2.getOdds1() + ", Odds 2: " + bet2.getOdds1());
                                            System.out.println("Suggested bet for Draw: " + stakePerBet / bet1.oddsDraw);
                                            System.out.println("findProfit.Bet draw on: " + b1.getBookieKey() + "\n");
                                            arbPairs.add(new ArbitragePresent(bet1, bet2, false, profit)); // this creates a list of bet pairs that have arbitrage

                                        } else if ((1 / bet1.odds2 + 1 / bet2.odds1 + 1 / bet2.oddsDraw) < 1.0) {

                                            double profit = 100 - (stakePerBet / bet1.odds2 + stakePerBet / bet2.odds1 + stakePerBet / bet2.oddsDraw);
                                            System.out.println("\nHome team: " + e.homeTeam + ", Away team: " + e.awayTeam);
                                            System.out.println("findProfit.Bookie 1: " + b1.getBookieKey() + ", findProfit.Bookie 2 " + b2.getBookieKey());
                                            System.out.println("Market: " + bet1.getMarket() + "\nTeam 1: " + bet1.getTeam2() + ", Team 2: " + bet2.getTeam1());
                                            System.out.println("Suggested bet on Team 1: " + stakePerBet / bet1.getOdds2() + ", Odds 1: " + bet1.getOdds2());
                                            System.out.println("Suggested bet on Team 2: " + stakePerBet / bet2.getOdds1() + ", Odds 2: " + bet2.getOdds1());
                                            System.out.println("Suggested bet for Draw: " + stakePerBet / bet2.oddsDraw);
                                            System.out.println("findProfit.Bet draw on: " + b2.getBookieKey() + "\n");
                                            arbPairs.add(new ArbitragePresent(bet1, bet2, false, profit)); // this creates a list of bet pairs that have arbitrage

                                        } else if ((1 / bet1.odds1 + 1 / bet2.odds2 + 1 / bet2.oddsDraw) < 1.0) {

                                            double profit = 100 - (stakePerBet / bet1.odds1 + stakePerBet / bet2.odds2 + stakePerBet / bet2.oddsDraw);
                                            System.out.println("\nHome team: " + e.homeTeam + ", Away team: " + e.awayTeam);
                                            System.out.println("findProfit.Bookie 1: " + b1.getBookieKey() + ", findProfit.Bookie 2 " + b2.getBookieKey());
                                            System.out.println("Market: " + bet1.getMarket() + "\nTeam 1: " + bet1.getTeam1() + ", Team 2: " + bet2.getTeam2());
                                            System.out.println("Suggested bet on Team 1: " + stakePerBet / bet1.getOdds1() + ", Odds 1: " + bet1.getOdds1());
                                            System.out.println("Suggested bet on Team 2: " + stakePerBet / bet2.getOdds2() + ", Odds 2: " + bet2.getOdds2());
                                            System.out.println("Suggested bet for Draw: " + stakePerBet / bet1.oddsDraw);
                                            System.out.println("findProfit.Bet draw on: " + b2.getBookieKey() + "\n");
                                            arbPairs.add(new ArbitragePresent(bet1, bet2, true, profit)); // this creates a list of bet pairs that have arbitrage
                                        }
                                    }


                                }
                            }
                        }
                    }

                }

            }
        }
    }

    //
    //getters and setters
    //
    public ArrayList<ArbitragePresent> getArbPairs(){
        return arbPairs;
    }

    //
    //other functions used to sort through arbPairs
    //
    public ArbitragePresent findHighestProfit(){ //this, and functions like this might be better if they printed. maybe not though for website
        double bestProfit = 0;
        int index = 0;
        for (int i = 0; i < arbPairs.size(); i++){
            if(arbPairs.get(i).getPercentProfit()>bestProfit){
                bestProfit = arbPairs.get(i).getPercentProfit();
                index = i;
            }
        }
        System.out.println(bestProfit);
        return arbPairs.get(index);
    }

}






