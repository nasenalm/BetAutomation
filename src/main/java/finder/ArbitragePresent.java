package finder;

public class ArbitragePresent {
    Bet bet1;
    Bet bet2;

    //Usually, one sports book will have odds that are inconsistent with all the other
    //Sports books, and therefore, comparing this inconsistent sports book against all other books
    //will create arbitrage. The inconsistent bookie will be stored in the string defined below.
    String offBook;

    boolean bet1odds1; // will be true if the odds1 is paired with bet1 (this will mean odds2 and bet2 are paired)
    double percentProfit;

    //constructor
    public ArbitragePresent(Bet a, Bet b, boolean match, double profit){
        bet1 = a;
        bet2 = b;
        bet1odds1 = match;
        percentProfit = profit;
    }

    //getters and setters
    public void setOffBook(String book){
        offBook = book;
    }
    public Bet getBet1(){
        return bet1;
    }
    public Bet getBet2(){
        return bet2;
    }
    public boolean isBet1odds1() {
        return bet1odds1;
    }
    public double getPercentProfit(){
        return percentProfit;
    }


}
