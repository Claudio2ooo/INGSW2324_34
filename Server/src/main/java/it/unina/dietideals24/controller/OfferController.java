package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.model.Offer;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import it.unina.dietideals24.service.interfaces.IOfferService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    @Qualifier("mainOfferService")
    private IOfferService offerService;

    @Autowired
    @Qualifier("mainEnglishAuctionService")
    private IEnglishAuctionService englishAuctionService;

    @Autowired
    @Qualifier("mainDownwardAuctionService")
    private IDownwardAuctionService downwardAuctionService;

    @GetMapping("english/{id}")
    public List<Offer> getOffersByEnglishAuctionId(@PathVariable("id") Long englishAuctionId) {
        return offerService.getOffersByEnglishAuctionId(englishAuctionId);
    }

    @GetMapping("downward/{id}")
    public List<Offer> getOffersByDownwardAuctionId(@PathVariable("id") Long downwardAuctionId) {
        return offerService.getOffersByDownwardAuctionId(downwardAuctionId);
    }

    @GetMapping("offerer/{id}")
    public List<Offer> getOffersByOffererId(@PathVariable("id") Long offererId) {
        return offerService.getOffersByOffererId(offererId);
    }

    @PostMapping("/english_auctions")
    public ResponseEntity<Offer> makeOfferForEnglishAuction(@PathVariable("id") Long englishAuctionId, @RequestParam BigDecimal newOffer) throws BadRequestException {
        EnglishAuction targetAuction = englishAuctionService.getEnglishAuctionById(englishAuctionId);
        if(offerIsBetter(targetAuction, newOffer)){
            Offer betterOffer = new Offer(
                    newOffer,
                    targetAuction.getOwner(),
                    targetAuction
            );
            Offer savedOffer = offerService.save(betterOffer);
            englishAuctionService.updateCurrentPrice(targetAuction, newOffer);
            //TODO FIX
            //auctionTimerController.restartOngoingEnglishTimer(targetAuction);

            return ResponseEntity.ok(savedOffer);
        } else {
            throw new BadRequestException("Offer is not valid, current price is higher");
        }

    }

    @PostMapping("/downward_auction")
    public ResponseEntity<Offer> makeOfferForDownwardAuction(@PathVariable("id") Long downwardAuctionId, @RequestParam BigDecimal newOffer) throws BadRequestException {
        DownwardAuction targetAuction = downwardAuctionService.getDownwardAuctionById(downwardAuctionId);
        if(offerIsBetter(targetAuction, newOffer)){
            Offer betterOffer = new Offer(
                    newOffer,
                    targetAuction.getOwner(),
                    targetAuction
            );
            Offer savedOffer = offerService.save(betterOffer);
            //TODO finalizza acquisto di asta ribasso
            return ResponseEntity.ok(savedOffer);
        } else {
            throw new BadRequestException("Offer is not valid, current price is higher");
        }
    }

    private boolean offerIsBetter(Auction targetAuction, BigDecimal newOffer) {
        return newOffer.compareTo(targetAuction.getCurrentPrice()) > 0;
    }
}
