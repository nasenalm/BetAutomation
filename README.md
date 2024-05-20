Project: Bet Automation (+EV betting on bovada)

IMPORTANT DISCLAIMER: although positive expected value sports betting is legal and profitabe, every sportstbook has their own terms and conditions that may ban betting with the use of software like this, giving bettors and unfair edge. Sportsbooks, including bovada, have the right to freeze funds or shut down accounts if they suspect that you are 'cheating'. Bet at your own risk!

Functionality: this project will receive odds for a variety of different sports from the-odds-api.com, find average fair odds for each event (average no-vig odds),
and then find the expected value for each bet by comparing the bookmakers odds with the average no vig odds. If a bet is both +EV and is on the bovada sportsbook, 
a microsoft edge window will pop up, controlled by selenium, and automatically place each +EV bet. It will then close the window once all bets are placed, log
out of bovada, and schedule another runtime for 1 hour after the initial runtime. Bets are to be placed every hour until 20 bets have been placed (with reccomended
bet size of 5% of the users total bankroll). If no +EV bets are found on bovada, the webdriver window will close, and the next runtime will be scheduled)

Setup: 
- The api key for the-odds-api is hardcoded in, and must be entered in the JsonParser.java file on line 14
- Login information for bovada must be entered in Selenium.java file on lines 96 and 97
- Microsoft Edge Driver must be locally downloaded, and the file location must be entered in BovadaAutomation.java file on line 11
- Bet size (reccomended 5% of bankroll) must be entered in BovadaAutomation.java file on line 12
- Maximum number of bets can be changed from 20 in BovadaAutomation.java file on line 21

Personalization / Possible Additional Use Cases:
This code is currently setup to only automate +EV spreads betting on Bovada, but the files located in the 'finder' directory find +EV bets for all bookmakers 
that are available with the-odds-api, and find spreads, totals, moneyline, h2h, h2h_lay and all other bet markets on the-odds-api. These +EV bets are stored
in an Arraylist of EvPresent objects, created with the EvPresent class, that could be used to manually place bets, or with some manipulation of the Selenium.java
file, could be used to automate bets on multiple bookmakers.

There is also a file called finder/ArbitrageChecker.java that is functional, but not currently being used, that will find arbitrage opportunities between any
two bookmakers on the-odds-api.
