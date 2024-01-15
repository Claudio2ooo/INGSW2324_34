package it.unina.dietideals24.controller;

import it.unina.dietideals24.enumeration.StateEnum;
import it.unina.dietideals24.model.*;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import it.unina.dietideals24.service.interfaces.INotificationService;
import it.unina.dietideals24.service.interfaces.IOfferService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    @Qualifier("mainOfferService")
    private IOfferService offerService;

    public void finalizeAuction(EnglishAuction englishAuction){
        if (noOffersReceived(englishAuction))
            createFailedAuctionNotification(englishAuction);
        else
            createNotifications(englishAuction);
        removeAuction(englishAuction);
    }

    private void createFailedAuctionNotification(EnglishAuction englishAuction) {
        DietiUser owner = englishAuction.getOwner();
        Notification failedAuctionNotification = new Notification(
                StateEnum.FALLITA,
                owner,
                englishAuction.getTitle(),
                englishAuction.getImageURL(),
                englishAuction.getCurrentPrice()
        );

        System.out.println("Failed english auction notification: "+failedAuctionNotification);
        notificationService.save(failedAuctionNotification);
    }

    private boolean noOffersReceived(EnglishAuction englishAuction) {
        return englishAuction.getCurrentPrice().equals(englishAuction.getStartingPrice());
    }

    private void removeAuction(EnglishAuction englishAuction) {
        englishAuctionService.deleteEnglishAuctionById(englishAuction.getId());
    }

    private void createNotifications(EnglishAuction englishAuction) {
        createLosersNotification(englishAuction);
        createWinnerNotification(englishAuction);
        createOwnerNotification(englishAuction);
    }

    private void createOwnerNotification(Auction auction){
        DietiUser owner = auction.getOwner();
        Notification ownerNotification = new Notification(
                StateEnum.CONCLUSA,
                owner,
                auction.getTitle(),
                auction.getImageURL(),
                auction.getCurrentPrice()
        );

        System.out.println("Owner notification: "+ownerNotification);
        notificationService.save(ownerNotification);
    }

    private void createWinnerNotification(EnglishAuction englishAuction){
        DietiUser winner = offerService.getWinner(englishAuction);
        Notification winnerNotification = new Notification(
                StateEnum.VINTA,
                winner,
                englishAuction.getTitle(),
                englishAuction.getImageURL(),
                englishAuction.getCurrentPrice()
        );

        System.out.println("Winner notification: "+winnerNotification);
        notificationService.save(winnerNotification);
    }

    private void createLosersNotification(EnglishAuction englishAuction){
        Set<DietiUser> losers = offerService.getLosers(englishAuction);
        List<Notification> losersNotification = new ArrayList<>();

        for (DietiUser l: losers) {
            losersNotification.add(new Notification(
                    StateEnum.PERSA,
                    l,
                    englishAuction.getTitle(),
                    englishAuction.getImageURL(),
                    englishAuction.getCurrentPrice()
                    )
            );
        }

        System.out.println("Losers notifications: "+losersNotification);
        notificationService.saveAll(losersNotification);
    }

    public void finalizeAuction(DownwardAuction downwardAuction){
        if (failed(downwardAuction)) {
            createFailedAuctionNotifications(downwardAuction);
        } else {
            createNotifications(downwardAuction);
        }
        removeAuction(downwardAuction);
    }

    private void createFailedAuctionNotifications(DownwardAuction downwardAuction) {
        DietiUser owner = downwardAuction.getOwner();
        Notification failedDownwardAuctionNotification = new Notification(
                StateEnum.FALLITA,
                owner,
                downwardAuction.getTitle(),
                downwardAuction.getImageURL(),
                downwardAuction.getStartingPrice()
        );

        System.out.println("Failed auction notification: " + downwardAuction.getTitle());
        notificationService.save(failedDownwardAuctionNotification);
    }

    private boolean failed(DownwardAuction downwardAuction) {
        return downwardAuction.getCurrentPrice().compareTo(downwardAuction.getMinimumPrice()) < 0;
    }

    private void removeAuction(DownwardAuction downwardAuction) {
        downwardAuctionService.deleteDownwardAuctionById(downwardAuction.getId());
    }

    private void createNotifications(DownwardAuction downwardAuction){
        createOwnerNotification(downwardAuction);
    }
}
