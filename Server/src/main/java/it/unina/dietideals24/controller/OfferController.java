package it.unina.dietideals24.controller;

import it.unina.dietideals24.auction_timer.DownwardAuctionTimerController;
import it.unina.dietideals24.auction_timer.EnglishAuctionTimerController;
import it.unina.dietideals24.dto.OfferDto;
import it.unina.dietideals24.model.*;
import it.unina.dietideals24.service.implementation.EnglishAuctionService;
import it.unina.dietideals24.service.interfaces.IDietiUserService;
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

    @Autowired
    @Qualifier("mainDietiUserService")
    private IDietiUserService dietiUserService;

    @Autowired
    private EnglishAuctionTimerController englishAuctionTimerController;

    @Autowired
    private DownwardAuctionTimerController downwardAuctionTimerController;

    @GetMapping("/english/{id}")
    public List<Offer> getOffersByEnglishAuctionId(@PathVariable("id") Long englishAuctionId) {
        return offerService.getOffersByEnglishAuctionId(englishAuctionId);
    }

    @GetMapping("/downward/{id}")
    public List<Offer> getOffersByDownwardAuctionId(@PathVariable("id") Long downwardAuctionId) {
        return offerService.getOffersByDownwardAuctionId(downwardAuctionId);
    }

    @GetMapping("offerer/{id}")
    public List<Offer> getOffersByOffererId(@PathVariable("id") Long offererId) {
        return offerService.getOffersByOffererId(offererId);
    }

    @PostMapping("/english")
    public ResponseEntity<Offer> makeOfferForEnglishAuction(@RequestBody OfferDto offerDto) throws BadRequestException {
        EnglishAuction targetAuction = englishAuctionService.getEnglishAuctionById(offerDto.getAuctionId());
        DietiUser offerer = dietiUserService.getUserById(offerDto.getOffererId());
        if(offerIsBetter(targetAuction, offerDto.getAmount())){
            Offer betterOffer = new Offer(
                    offerDto.getAmount(),
                    offerer,
                    targetAuction
            );
            Offer savedOffer = offerService.save(betterOffer);
            englishAuctionService.updateCurrentPrice(targetAuction, offerDto.getAmount());
            englishAuctionTimerController.restartOngoingEnglishTimer(targetAuction);

            return ResponseEntity.ok(savedOffer);
        } else {
            throw new BadRequestException("Offer is not valid, current price is higher");
        }

    }

    @PostMapping("/downward")
    public ResponseEntity<String> makeOfferForDownwardAuction(@RequestBody OfferDto offerDto) throws BadRequestException {
        //TODO IMPLEMENTARE BENE QUESTO METODO

        DownwardAuction targetAuction = downwardAuctionService.getDownwardAuctionById(offerDto.getAuctionId());
        //NOTIFICA A UTENTE

        downwardAuctionService.deleteDownwardAuctionById(offerDto.getAuctionId());

        return ResponseEntity.ok("Congratulazioni!");
    }

    private boolean offerIsBetter(Auction targetAuction, BigDecimal newOffer) {
        return newOffer.compareTo(targetAuction.getCurrentPrice()) > 0;
    }
}
