package automation;

import finder.CheckPlusEV;
import finder.EvPresent;
import finder.EventCreator;
import finder.JsonParser;
import org.json.simple.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// Link mysql database that logs all bets

public class Selenium{
    String driverLocation;
    int newBetsPlaced = 0;
    int betAmt;
    ArrayList<String> teamsBetOn = new ArrayList<>();

    public Selenium(String driverLocation,int betAmt, ArrayList<String> teamsBetOn){
        this.driverLocation = driverLocation;
        this.teamsBetOn.addAll(teamsBetOn);
        this.betAmt = betAmt;

    }
    public void startAutomation(){
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.edge.driver", driverLocation); // Replace with the path to your Microsoft Edge WebDriver executable
        EdgeDriver driver = new EdgeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //count number of bets placed


        try {
            // open web address and login
            login(driver);
            wait(10000);

            //Navigate to Sports Page and Switch to decimal odds
            setupSports(driver, wait);
            wait(3000);

            // Find bets
            JsonParser parser = new JsonParser();
            JSONArray joA = parser.getJoA(); //create in main method
            EventCreator c = new EventCreator();
            c.createEvents(joA); // c will hold an arraylist of events which hold their own bets. This will have all books
            CheckPlusEV evChecker = new CheckPlusEV(c.getEvents(), "spreads");
            evChecker.getPlusEV().removeIf(ev -> !(ev.getBookie().equals("bovada")));

            for(EvPresent ev: evChecker.getPlusEV()){
                String teamName = ev.getBetOn();
                String market = ev.getMarket();
                Double odds = ev.getOdds();
                Double line = ev.getLine();
                // Find the search bar and enter text
                navToEvent(driver, wait, teamName);
                wait(5000);

                // Compare odds from bovada with expected odds from the-odds-api (ensure odds haven't changed)
                compareOdds(driver, wait, odds, market, teamName);

                wait(13000); // wait time between bets
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            System.out.println("done");
            driver.quit();
        }
    }

    public void login(EdgeDriver driver){
        // Navigate to Bovada.lv
        driver.get("https://www.bovada.lv/login");

        try {
            Thread.sleep(10000); // 10000 milliseconds = 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Enter your login credentials
        WebElement usernameInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        usernameInput.sendKeys(""); // BOVADA USERNAME / EMAIL
        passwordInput.sendKeys(""); // BOVADA PASSWORD

        // Find and click the login button
        WebElement submitButton = driver.findElement(By.id("login-submit"));
        submitButton.click();
    }

    public void setupSports(EdgeDriver driver, WebDriverWait wait){
        WebElement sportsButton = driver.findElement(By.xpath("/html/body/bx-site/ng-component/bx-header-ch/div/nav/ul/li[1]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(sportsButton));
        sportsButton.click();

        try {
            Thread.sleep(10000); // 10000 milliseconds = 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //switch to decimal odds
        WebElement oddsDropdown = driver.findElement(By.xpath("/html/body/bx-site/ng-component/div[1]/sp-sports-ui/div/main/div/section/main/sp-home/div/div/sp-odds-format-selector/bx-dropdown/figure"));
        // Scroll the element into the center of the viewport
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center'});", oddsDropdown);
        // Wait for the element to be clickable
        WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(oddsDropdown));
        // Click on the element
        clickableElement.click();

        try{
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        WebElement oddsSelect = driver.findElement(By.xpath("/html/body/bx-site/ng-component/div[1]/sp-sports-ui/div/main/div/section/main/sp-home/div/div/sp-odds-format-selector/bx-dropdown/figure/ul/li[2]"));
        oddsSelect.click();

    }

    public void navToEvent(EdgeDriver driver, WebDriverWait wait, String eventName){
        WebElement searchInput = driver.findElement(By.id("small-dropdown-search-input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center'});", searchInput);
        searchInput.sendKeys(eventName);

        try {
            Thread.sleep(5000); // 10000 milliseconds = 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement gameButton = driver.findElement(By.xpath("//*[@id=\"search-results-dropdown\"]/ul/li[1]"));
        // add try block. A game might not show up at all...
        wait.until(ExpectedConditions.elementToBeClickable(gameButton));
        gameButton.click();
    }

    public void compareOdds(EdgeDriver driver, WebDriverWait wait, Double odds, String market, String team){
        String text;
        String teamTop = driver.findElement(By.xpath("/html/body/bx-site/ng-component/div[1]/sp-sports-ui/div/main/div/section/main/sp-event/div/div/div/sp-coupon[1]/sp-multi-markets/section/section/header/sp-competitor-coupon/a/div[1]/h4[1]/span[1]")).getText();
        System.out.println("Top Team (Bovada): " + teamTop);
        String teamBottom = driver.findElement(By.xpath("/html/body/bx-site/ng-component/div[1]/sp-sports-ui/div/main/div/section/main/sp-event/div/div/div/sp-coupon[1]/sp-multi-markets/section/section/header/sp-competitor-coupon/a/div[1]/h4[2]/span[1]")).getText();
        System.out.println("Bottom Team (Bovada): " + teamTop);
        System.out.println("+EV on: " + team);
        List<WebElement> betPrices = driver.findElements(By.className("bet-price"));
        if (market.equals("spreads") && team.equals(teamTop) && !(teamsBetOn.contains(team))) { // locate correct bet and ensure not already bet on
            wait(2000); // eventually change to a webdriver wait function

            // if odds shift, or page refreshes, elements in betPrices list become stale
            WebElement betPrice = betPrices.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center'});", betPrice); // scroll to center of screen
            text = betPrice.getText();
            text = text.replaceAll("[()]", ""); // remove all parenthesis
            double oddsDecimal = Double.parseDouble(text);
            oddsDecimal = Math.round(oddsDecimal * 100.0) / 100.0;
            System.out.println(oddsDecimal);
            if (oddsDecimal == odds) { // add logic ensuring spread line is the same
                placeBet(driver, wait, 0, "4");
                System.out.println("new bet placed on: " + team);
                teamsBetOn.add(team);
            }
        } else if (market.equals("spreads") && team.equals(teamBottom) && !(teamsBetOn.contains(team))) {
            wait(3000);

            // if odds shift, or page refreshes, elements in betPrices list become stale
            WebElement betPrice = betPrices.get(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center'});", betPrice); // scroll to center of screen
            text = betPrice.getText();
            text = text.replaceAll("[()]", ""); // remove all parenthesis
            double oddsDecimal = Double.parseDouble(text);
            oddsDecimal = Math.round(oddsDecimal * 100.0) / 100.0;
            System.out.println(oddsDecimal);
            if (oddsDecimal == odds) { // see comment on first conditional
                placeBet(driver, wait, 1, "4");
                System.out.println("new bet placed on: " + team);
                teamsBetOn.add(team);
            }
        }
    }

    public void placeBet(EdgeDriver driver, WebDriverWait wait, int buttonIndex, String betAmt){

        //Although longer wait time will decrease loading errors, it creates a higher risk of shifting odds
        wait(5000);
        List<WebElement> betButtons = driver.findElements(By.className("bet-btn"));
        betButtons.get(buttonIndex).click();
        wait(6000);
        WebElement betAmtField = driver.findElement(By.id("default-input--risk"));
        wait(3000);
        betAmtField.sendKeys(betAmt);
        wait(3600);
        driver.findElement(By.className("place-bets custom-cta primary cta-large betTabs")).click();
        newBetsPlaced += 1;
        System.out.println(newBetsPlaced + " Bet(s) placed");

    }

    public void wait(int mills){
        try {
            Thread.sleep(mills); // 10000 milliseconds = 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getBankroll(){
        return null;
    }

    public int getNewBetsPlaced(){
        return newBetsPlaced;
    }

    public ArrayList<String> getTeamsBetOn(){
        return teamsBetOn;
    }

}