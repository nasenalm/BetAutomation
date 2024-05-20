package finder;

import java.util.ArrayList;

public class CheckPlusEV {

    ArrayList<EvPresent> plusEV = new ArrayList<>();

    // Constructs a CheckPlusEV object, populating the arraylist plusEV with bets that have odds with a positive EV percentage.
    public CheckPlusEV(ArrayList<Event> events, String betType) { // takes market as a param (need to run multiple times to search across multiple markets
        for (Event event : events) {
            int x = 0;
            double fairOddsSum1 = 0;
            double fairOddsSum2 = 0;
            double fairOddsSumD = 0;
            ArrayList<LineFairOddsSum> lineFairOddsSums = new ArrayList<>();

            // Loop through the bookies and bets for the specified bet type
            for (Bookie bookie : event.bookies) {
                for (Bet bet : bookie.bets) {
                    // Check the bet type
                    if (bet.getMarket().equals(betType)) {
                        double line = bet.getTotalPoint();

                        // If it's an over/under bet, add to the sum for the specific line
                        if (betType.equals("totals") || betType.equals("spreads")) {
                            boolean found = false;
                            for (LineFairOddsSum lineFairOddsSum : lineFairOddsSums) {
                                if (lineFairOddsSum.getLine() == line) {
                                    lineFairOddsSum.addToSum(bet.getFairOdds1(), bet.getFairOdds2(),bet.getFairOddsDraw());
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                LineFairOddsSum newLineFairOddsSum = new LineFairOddsSum(line);
                                newLineFairOddsSum.addToSum(bet.getFairOdds1(), bet.getFairOdds2(),bet.getFairOddsDraw());
                                lineFairOddsSums.add(newLineFairOddsSum);
                            }
                        } else {
                            // For other bet types, calculate the sum as usual
                            fairOddsSum1 += bet.getFairOdds1();
                            fairOddsSum2 += bet.getFairOdds2();
                            fairOddsSumD += bet.getFairOddsDraw();
                        }
                        x++;
                    }
                }
            }

            // Calculate average fair odds for the specified bet type
            double avgFairOdds1 = 0;
            double avgFairOdds2 = 0;
            double avgFairOddsD = 0;
            if (betType.equals("totals") || betType.equals("spreads")) {
                for (LineFairOddsSum lineFairOddsSum : lineFairOddsSums) {
                    avgFairOdds1 = lineFairOddsSum.getAverageFairOdds1();
                    avgFairOdds2 = lineFairOddsSum.getAverageFairOdds2();
                    avgFairOddsD = lineFairOddsSum.getAverageFairOddsD();
                }

            } else {
                avgFairOdds1 = fairOddsSum1 / x;
                avgFairOdds2 = fairOddsSum2 / x;
                avgFairOddsD = fairOddsSumD / x;
            }

            // Loop through the bets again after averages are collected
            for (Bookie bookie : event.bookies) {
                for (Bet bet : bookie.bets) {
                    // Check the bet type and compare with the corresponding average fair odds
                    if (bet.getMarket().equals(betType)) {

                        if (bet.getOdds1() > avgFairOdds1) {
                            double ev = (bet.getOdds1() - avgFairOdds1) * (1 / avgFairOdds1 * 100);
                            plusEV.add(new EvPresent(bet.getOdds1(), avgFairOdds1, ev, bet.getMarket(), bet.getBookMaker(), bet.getTeam1(), bet.getTeam2(), bet.getTotalPoint(), bet.getTeam1()));

                        } else if (bet.getOdds2() > avgFairOdds2) {
                            double ev = (bet.getOdds2() - avgFairOdds2) * (1 / avgFairOdds2 * 100);
                            plusEV.add(new EvPresent(bet.getOdds2(), avgFairOdds2, ev, bet.getMarket(), bet.getBookMaker(), bet.getTeam1(), bet.getTeam2(), bet.getTotalPoint(), bet.getTeam2()));

                        } else if (bet.getOddsDraw() > avgFairOddsD) {
                            double ev = (bet.getOddsDraw() - avgFairOddsD) * (1 / avgFairOddsD * 100);
                            plusEV.add(new EvPresent(bet.getOddsDraw(), avgFairOddsD, ev, bet.getMarket(), bet.getBookMaker(), bet.getTeam1(), bet.getTeam2(), bet.getTotalPoint(), "draw"));
                        }


                    }
                }
            }
        }
    }


    public ArrayList<EvPresent> getPlusEV () {
        return plusEV;
    }
}