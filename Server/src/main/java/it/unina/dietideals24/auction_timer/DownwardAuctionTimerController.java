package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Timer;

@Controller
public class DownwardAuctionTimerController {
    private final static HashMap<Long, Timer> downwardAuctionTimers = new HashMap<>();

    /**
     * Starts a timer for an auction
     * @param auction auction whose timer starts
     */
    public void startNewTimer(Auction auction){
        Long position = auction.getId();
        long countdownInMilliseconds = auction.getTimerInMilliseconds();

        Timer timer = new Timer();
        DownwardAuctionTask downwardAuctionTask = new DownwardAuctionTask();
        downwardAuctionTask.setAuction(auction);

        timer.schedule(downwardAuctionTask, countdownInMilliseconds);
        downwardAuctionTimers.put(position, timer);
    }

    /**
     * Stops an ongoing timer of a downwardAuction
     * @param downwardAuction downwardAuction whose timer gets stopped
     */
    public void stopOngoingDownwardTimer(DownwardAuction downwardAuction){
        Long position = downwardAuction.getId();

        Timer toBeStopped = downwardAuctionTimers.get(position);
        toBeStopped.cancel();
        toBeStopped.purge();
        downwardAuctionTimers.remove(position);
    }


}
