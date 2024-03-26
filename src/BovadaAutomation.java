import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class BovadaAutomation {

    public static void main(String[] args) throws InterruptedException {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.edge.driver", "/Users/nasen/Downloads/edgedriver_win64/msedgedriver.exe"); // Replace with the path to your Microsoft Edge WebDriver executable

        // Create a new instance of the EdgeDriver
        // Create an instance of the EdgeDriverService

        // Start the EdgeDriver with the EdgeDriverService
        EdgeDriver driver = new EdgeDriver();

        // Set implicit wait to handle synchronization issues
        Thread.sleep(100);

        try {
            // Navigate to Bovada.lv
            driver.get("https://www.bovada.lv/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                Thread.sleep(10000); // 10000 milliseconds = 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // Enter your login credentials
            WebElement usernameInput = driver.findElement(By.id("email"));
            WebElement passwordInput = driver.findElement(By.id("login-password"));
            usernameInput.sendKeys("nasenalm@icloud.com");
            passwordInput.sendKeys("5Ports8etting!");

            // Find and click the login button
            WebElement submitButton = driver.findElement(By.id("login-submit"));
            submitButton.click();

            // Wait for the login to complete (you might need to adjust the wait time)
            try {
                Thread.sleep(10000); // 10000 milliseconds = 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Navigate to Sports Page
            WebElement sportsButton = driver.findElement(By.xpath("/html/body/bx-site/ng-component/bx-header-ch/div/nav/ul/li[1]/a"));
            wait.until(ExpectedConditions.elementToBeClickable(sportsButton));
            sportsButton.click();

            try {
                Thread.sleep(10000); // 10000 milliseconds = 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Identify current bets on bovada


            // Find the search bar and enter text
            WebElement searchInput = driver.findElement(By.id("small-dropdown-search-input"));
            searchInput.sendKeys("tottenham");


            try {
                Thread.sleep(5000); // 10000 milliseconds = 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement gameButton = driver.findElement(By.xpath("//*[@id=\"search-results-dropdown\"]/ul/li[1]"));
            wait.until(ExpectedConditions.elementToBeClickable(gameButton));
            gameButton.click();

            try {
                Thread.sleep(5000); // 10000 milliseconds = 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String text;
            text = driver.findElement(By.xpath("/html/body/bx-site/ng-component/div[1]/sp-sports-ui/div/main/div/section/main/sp-event/div/div/div/sp-coupon[1]/sp-multi-markets/section/section/sp-outcomes/sp-three-way-vertical[1]/ul/li[1]/sp-outcome/button/span[1]")).getText();
            text = text.substring(1,text.length()-1);
            int oddsAmerican = Integer.parseInt(text);
            //convert american odds to decimal, or ensure that odds are decimal on every login.

            // Perform actions on the resulting page
            // ...

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            System.out.println("done");
            //driver.quit();
        }
    }
}
