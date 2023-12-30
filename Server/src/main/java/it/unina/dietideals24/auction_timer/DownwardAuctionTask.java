package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.DownwardAuctionController;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import org.springframework.beans.factory.annotation.Autowired;

public class DownwardAuctionTask extends AuctionTask {

    private final DownwardAuctionController downwardAuctionController;

    public DownwardAuctionTask(Auction auction, DownwardAuctionController downwardAuctionController) {
        super(auction);
        this.downwardAuctionController = downwardAuctionController;
    }

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
