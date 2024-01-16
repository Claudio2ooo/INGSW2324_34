package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.FinalizePurchaseController;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Timer;

@Controller
public class DownwardAuctionTimerController {
    private final static HashMap<Long, Timer> downwardAuctionTimers = new HashMap<>();

    @Autowired
    @Qualifier("mainDownwardAuctionService")
    private IDownwardAuctionService downwardAuctionService;

    @Autowired
    private FinalizePurchaseController finalizePurchaseController;



    /**
     * Starts a timer for an auction
     * @param downwardAuction auction whose timer starts
     */
    public void startNewTimer(DownwardAuction downwardAuction){
        Long position = downwardAuction.getId();
        long countdownInMilliseconds = downwardAuction.getTimerInMilliseconds();

        Timer timer = new Timer();
        DownwardAuctionTask downwardAuctionTask = new DownwardAuctionTask(finalizePurchaseController, downwardAuctionService, downwardAuction);

        timer.scheduleAtFixedRate(downwardAuctionTask, countdownInMilliseconds, countdownInMilliseconds);
        downwardAuctionTimers.put(position, timer);
        System.out.println("Timer started for downward auction "+downwardAuction.getTitle());
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
