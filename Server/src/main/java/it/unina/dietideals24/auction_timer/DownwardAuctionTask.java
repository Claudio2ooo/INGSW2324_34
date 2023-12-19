package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.DownwardAuctionController;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import org.springframework.beans.factory.annotation.Autowired;

public class DownwardAuctionTask extends AuctionTask {
    @Autowired
    private DownwardAuctionController downwardAuctionController;

    public DownwardAuctionTask(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        DownwardAuction downwardAuction = (DownwardAuction) getAuction();

        if(downwardAuction.canBeDecreased()){
            //TODO implementare questo metodo
            //downwardAuctionController.decreasePriceDownwardAuction(getAuction().getId());
            cancel();
        } else {
            downwardAuctionController.deleteDownwardAuction(getAuction().getId());
            cancel();
        }

    }
}
