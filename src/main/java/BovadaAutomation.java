import automation.Selenium;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BovadaAutomation {

    private static final String DRIVER_PATH = ""; // ENTER PATH TO EDGE DRIVER
    private static final int BET_SIZE = 0; // CHANGE 0 TO 5% OF BANKROLL

    private static ArrayList<String> teamsBetOn = new ArrayList<>();
    private static AtomicInteger totalBets = new AtomicInteger();

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            if (totalBets.get() >= 20) {
                executor.shutdown();
            }
            Selenium selenium = new Selenium(DRIVER_PATH, BET_SIZE, teamsBetOn);
            selenium.startAutomation();
            totalBets.addAndGet(selenium.getNewBetsPlaced());
            teamsBetOn.addAll(selenium.getTeamsBetOn());
            LocalTime currentTime = LocalTime.now();
            LocalTime scheduledTime = currentTime.plusHours(1);
            System.out.println("Sleeping... Next execution at: " + scheduledTime);
        };

        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }
}
