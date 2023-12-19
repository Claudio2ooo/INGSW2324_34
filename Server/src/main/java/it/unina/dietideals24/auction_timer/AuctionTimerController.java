package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;

import java.util.ArrayList;
import java.util.Timer;

public class AuctionTimerController {
    ArrayList<Timer> englishAuctionTimers = new ArrayList<>();
    ArrayList<Timer> downwardAuctionTimers = new ArrayList<>();

    /**
     * Starts a timer for an auction
     * @param auction auction whose timer starts
     */
    public void startNewTimer(Auction auction){
        int position = Math.toIntExact(auction.getId());
        long countdownInMilliseconds = auction.getTimerInMilliseconds();

        Timer timer = new Timer();
        if(auction instanceof EnglishAuction){
            timer.schedule(new EnglishAuctionTask(auction), countdownInMilliseconds);
            englishAuctionTimers.add(position, timer);
        } else {
            timer.schedule(new DownwardAuctionTask(auction), countdownInMilliseconds);
            downwardAuctionTimers.add(position, timer);
        }
    }

    /**
     * Stops an ongoing timer of a downwardAuction
     * @param downwardAuction downwardAuction whose timer gets stopped
     */
    public void stopOngoingDownwardTimer(DownwardAuction downwardAuction){
        int position = Math.toIntExact(downwardAuction.getId());

        Timer toBeStopped = downwardAuctionTimers.get(position);
        toBeStopped.cancel();
        toBeStopped.purge();
        downwardAuctionTimers.remove(position);
    }

    /**
     * Restarts the timer of an englishAuction
     * @param englishAuction englishAuction whose timer gets restarted
     */
    public void restartOngoingEnglishTimer(EnglishAuction englishAuction){
        int position = Math.toIntExact(englishAuction.getId());
        Long countdownInMilliseconds = englishAuction.getTimerInMilliseconds();

        Timer toBeRestarted = englishAuctionTimers.get(position);
        toBeRestarted.cancel();
        toBeRestarted.purge();

        toBeRestarted.schedule(new EnglishAuctionTask(englishAuction), countdownInMilliseconds);
    }
}
