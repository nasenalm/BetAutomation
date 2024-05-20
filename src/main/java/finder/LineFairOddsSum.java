package finder;

public class LineFairOddsSum {
    private double line;
    private double sum1;
    private double sum2;
    private double sumd;
    private int count;

    public LineFairOddsSum(double line) {
        this.line = line;
        this.sum1 = 0;
        this.sum2 = 0;
        this.sumd = 0;
        this.count = 0;
    }

    public void addToSum(double fairOdds1, double fairOdds2, double fairOddsD) {
        this.sum1 += fairOdds1;
        this.sum2 += fairOdds2;
        this.sumd += fairOddsD;
        this.count++;
    }

    public double getAverageFairOdds1() {
        return (count != 0) ? sum1 / count : 0;
    }
    public double getAverageFairOdds2() {
        return (count != 0) ? sum2 / count : 0;
    }
    public double getAverageFairOddsD() {
        return (count != 0) ? sumd / count : 0;
    }

    public double getLine() {
        return line;
    }
}
