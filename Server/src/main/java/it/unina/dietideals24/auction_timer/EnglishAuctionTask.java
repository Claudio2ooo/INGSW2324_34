package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.service.implementation.EnglishAuctionService;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import lombok.Getter;
import lombok.Setter;

import java.util.TimerTask;

@Getter
@Setter
public class EnglishAuctionTask extends TimerTask {

    private IEnglishAuctionService englishAuctionService;
    private Auction auction;

    public EnglishAuctionTask(IEnglishAuctionService englishAuctionService, Auction auction) {
        this.englishAuctionService = englishAuctionService;
        this.auction = auction;
    }

    @Override
    public void run() {
        //TODO metodo che finalizza acquisto
        englishAuctionService.deleteEnglishAuctionById(getAuction().getId());
        cancel(); //buona pratica metterlo alla fine anche se non strettamente necessario
    }
}
