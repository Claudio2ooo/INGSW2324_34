package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import it.unina.dietideals24.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class FinalizePurchaseController {

    @Autowired
    @Qualifier("mainEnglishAuctionService")
    private IEnglishAuctionService englishAuctionService;

    @Autowired
    @Qualifier("mainDownwardAuctionService")
    private IDownwardAuctionService downwardAuctionService;

    @Autowired
    @Qualifier("mainNotificationService")
    private INotificationService notificationService;

    public void finalizeEnglishAuction(EnglishAuction englishAuction){
        createNotifications(englishAuction);
        removeAuction(englishAuction);
    }

    private void removeAuction(EnglishAuction englishAuction) {
        englishAuctionService.deleteEnglishAuctionById(englishAuction.getId());
    }

    private void createNotifications(EnglishAuction englishAuction) {
        //TODO creaNotifichePerdenti
        //TODO creaNotificheVincenti
        //TODO creaNotificheProprietario
    }

    public void finalizeDownwardAuction(DownwardAuction downwardAuction){
        createNotifications(downwardAuction);
        removeAuction(downwardAuction);
    }

    private void removeAuction(DownwardAuction downwardAuction) {
        downwardAuctionService.deleteDownwardAuctionById(downwardAuction.getId());
    }

    private void createNotifications(DownwardAuction downwardAuction){
        //TODO creaNotificheProprietario
    }
}
