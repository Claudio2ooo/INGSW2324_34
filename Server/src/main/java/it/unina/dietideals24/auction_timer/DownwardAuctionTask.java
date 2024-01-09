package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.DownwardAuctionController;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimerTask;

@Getter
@Setter
public class DownwardAuctionTask extends TimerTask {

    @Autowired
    private DownwardAuctionController downwardAuctionController;
    private Auction auction;


    @Override
    public void run() {
        DownwardAuction downwardAuction = (DownwardAuction) getAuction();

        if(downwardAuction.canBeDecreased()){
            downwardAuctionController.decreaseCurrentPrice(getAuction().getId());
            cancel();
        } else {
            //TODO inviare notifica al venditore
            downwardAuctionController.deleteDownwardAuction(getAuction().getId());
            cancel();
        }

    }
}
