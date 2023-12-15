package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.Offer;
import it.unina.dietideals24.service.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    @Qualifier("mainOfferService")
    private IOfferService offerService;

    @GetMapping("/english")
    public List<Offer> getOffersByEnglishAuctionId(@RequestParam Long englishAuctionId){
        return offerService.getOffersByEnglishAuctionId(englishAuctionId);
    }

    @GetMapping("/reverse")
    public List<Offer> getOffersByReverseAuctionId(@RequestParam Long reverseAuctionId){
        return offerService.getOffersByReverseAuctionId(reverseAuctionId);
    }

    @GetMapping("/user")
    public List<Offer> getOffersByOffererId(@RequestParam Long offererId){
        return offerService.getOffersByOffererId(offererId);
    }
}
