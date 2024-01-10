package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.service.implementation.DownwardAuctionService;
import lombok.Getter;
import lombok.Setter;

import java.util.TimerTask;

@Getter
@Setter
public class DownwardAuctionTask extends TimerTask {

    private DownwardAuctionService downwardAuctionService;
    private DownwardAuction downwardAuction;

    public DownwardAuctionTask(DownwardAuctionService downwardAuctionService, DownwardAuction downwardAuction) {
        this.downwardAuctionService = downwardAuctionService;
        this.downwardAuction = downwardAuction;
    }

    @Override
    public void run() {
        DownwardAuction downwardAuction = getDownwardAuction();

        if(downwardAuction.canBeDecreased()){
            downwardAuctionService.decreaseCurrentPrice(getDownwardAuction().getId());
            downwardAuction.decreaseCurrentPrice();
        } else {
            //TODO inviare notifica al venditore
            downwardAuctionService.deleteDownwardAuctionById(getDownwardAuction().getId());
            cancel();
        }

    }
}
