package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.EnglishAuctionController;
import it.unina.dietideals24.model.Auction;
import org.springframework.beans.factory.annotation.Autowired;

public class EnglishAuctionTask extends AuctionTask {

    @Autowired
    private EnglishAuctionController englishAuctionController;

    public EnglishAuctionTask(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        //TODO metodo che finalizza acquisto
        englishAuctionController.deleteEnglishAuction(getAuction().getId());
        cancel(); //buona pratica metterlo alla fine anche se non strettamente necessario
    }
}
