package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.controller.FinalizePurchaseController;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import lombok.Getter;
import lombok.Setter;

import java.util.TimerTask;

@Getter
@Setter
public class EnglishAuctionTask extends TimerTask {

    private FinalizePurchaseController finalizePurchaseController;
    private IEnglishAuctionService englishAuctionService;
    private EnglishAuction auction;

    public EnglishAuctionTask(FinalizePurchaseController finalizePurchaseController, IEnglishAuctionService englishAuctionService, EnglishAuction auction) {
        this.finalizePurchaseController = finalizePurchaseController;
        this.englishAuctionService = englishAuctionService;
        this.auction = auction;
    }

    @Override
    public void run() {
        finalizePurchaseController.finalizeAuction(auction);
        englishAuctionService.deleteEnglishAuctionById(getAuction().getId());
        cancel(); //buona pratica metterlo alla fine anche se non strettamente necessario
    }
}
