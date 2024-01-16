package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.FinalizePurchaseController;
import it.unina.dietideals24.model.EnglishAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Timer;

@Controller
public class EnglishAuctionTimerController {
    private static final HashMap<Long, Timer> englishAuctionTimers = new HashMap<>();

    @Autowired
    private FinalizePurchaseController finalizePurchaseController;

    /**
     * Starts a timer for an auction
     * @param auction auction whose timer starts
     */
    public void startNewTimer(EnglishAuction auction){
        Long auctionId = auction.getId();
        long countdownInMilliseconds = auction.getTimerInMilliseconds();

        Timer timer = new Timer();

        EnglishAuctionTask englishAuctionTask = new EnglishAuctionTask(finalizePurchaseController, auction);
        timer.schedule(englishAuctionTask, countdownInMilliseconds);

        englishAuctionTimers.put(auctionId, timer);
        System.out.println("Timer started for english auction "+ auction.getTitle());
    }

    /**
     * Restarts the timer of an englishAuction
     * @param englishAuction englishAuction whose timer gets restarted
     */
    public void restartOngoingEnglishTimer(EnglishAuction englishAuction){
        Long auctionId = englishAuction.getId();
        Long countdownInMilliseconds = englishAuction.getTimerInMilliseconds();

        Timer toBeRestarted = englishAuctionTimers.get(auctionId);
        toBeRestarted.cancel();

        toBeRestarted = new Timer();

        EnglishAuctionTask englishAuctionTask = new EnglishAuctionTask(finalizePurchaseController, englishAuction);

        toBeRestarted.schedule(englishAuctionTask, countdownInMilliseconds);
        System.out.println("Timer restarted for english auction "+ englishAuction.getTitle());
    }
}
