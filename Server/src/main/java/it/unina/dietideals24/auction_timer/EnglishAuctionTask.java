package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.service.implementation.EnglishAuctionService;

public class EnglishAuctionTask extends AuctionTask {

    private final EnglishAuctionService englishAuctionService;

    public EnglishAuctionTask(Auction auction, EnglishAuctionService englishAuctionService)
    {
        super(auction);
        this.englishAuctionService = englishAuctionService;
    }


    @Override
    public void run() {
        //TODO metodo che finalizza acquisto
        englishAuctionService.deleteEnglishAuctionById(getAuction().getId());
        cancel(); //buona pratica metterlo alla fine anche se non strettamente necessario
    }
}
