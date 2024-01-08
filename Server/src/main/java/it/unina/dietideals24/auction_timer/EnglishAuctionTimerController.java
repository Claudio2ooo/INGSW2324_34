package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.service.implementation.EnglishAuctionService;

import java.util.HashMap;
import java.util.Timer;

public class EnglishAuctionTimerController {
    private static final HashMap<Long, Timer> englishAuctionTimers = new HashMap<>();

    /**
     * Starts a timer for an auction
     * @param auction auction whose timer starts
     */
    public void startNewTimer(Auction auction, EnglishAuctionService englishAuctionService){
        Long auctionId = auction.getId();
        long countdownInMilliseconds = auction.getTimerInMilliseconds();

        Timer timer = new Timer();
        timer.schedule(new EnglishAuctionTask(auction, englishAuctionService), countdownInMilliseconds);
        englishAuctionTimers.put(auctionId, timer);
        System.out.println("Timer started for english auction "+ auction.getTitle());
    }

    /**
     * Restarts the timer of an englishAuction
     * @param englishAuction englishAuction whose timer gets restarted
     */
    public void restartOngoingEnglishTimer(EnglishAuction englishAuction, EnglishAuctionService englishAuctionService){
        Long auctionId = englishAuction.getId();
        Long countdownInMilliseconds = englishAuction.getTimerInMilliseconds();

        Timer toBeRestarted = englishAuctionTimers.get(auctionId);
        toBeRestarted.cancel();
        toBeRestarted.purge();
        System.out.println("Timer stopped for english auction "+ englishAuction.getTitle());

        toBeRestarted = new Timer();
        toBeRestarted.schedule(new EnglishAuctionTask(englishAuction, englishAuctionService), countdownInMilliseconds);
        System.out.println("Timer restarted for english auction "+ englishAuction.getTitle());
    }
}
