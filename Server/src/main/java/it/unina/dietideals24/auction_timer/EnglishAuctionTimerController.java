package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.EnglishAuctionController;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;

import java.util.HashMap;
import java.util.Timer;

public class EnglishAuctionTimerController {
    HashMap<Long, Timer> englishAuctionTimers = new HashMap<>();
    private final EnglishAuctionController englishAuctionController;

    public EnglishAuctionTimerController(EnglishAuctionController englishAuctionController) {
        this.englishAuctionController = englishAuctionController;
    }

    /**
     * Starts a timer for an auction
     * @param auction auction whose timer starts
     */
    public void startNewTimer(Auction auction){
        Long position = auction.getId();
        long countdownInMilliseconds = auction.getTimerInMilliseconds();

        Timer timer = new Timer();
        timer.schedule(new EnglishAuctionTask(auction, englishAuctionController), countdownInMilliseconds);
        englishAuctionTimers.put(position, timer);
        System.out.println("Timer started for english auction "+ auction.getTitle());
    }

    /**
     * Restarts the timer of an englishAuction
     * @param englishAuction englishAuction whose timer gets restarted
     */
    public void restartOngoingEnglishTimer(EnglishAuction englishAuction){
        Long position = englishAuction.getId();
        Long countdownInMilliseconds = englishAuction.getTimerInMilliseconds();

        Timer toBeRestarted = englishAuctionTimers.get(position);
        toBeRestarted.cancel();
        toBeRestarted.purge();

        toBeRestarted.schedule(new EnglishAuctionTask(englishAuction, englishAuctionController), countdownInMilliseconds);
    }
}
