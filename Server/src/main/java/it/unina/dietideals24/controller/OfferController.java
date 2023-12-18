package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.Offer;
import it.unina.dietideals24.service.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    @Qualifier("mainOfferService")
    private IOfferService offerService;

    @GetMapping("english/{id}")
    public List<Offer> getOffersByEnglishAuctionId(@PathVariable("id") Long englishAuctionId) {
        return offerService.getOffersByEnglishAuctionId(englishAuctionId);
    }

    @GetMapping("reverse/{id}")
    public List<Offer> getOffersByReverseAuctionId(@PathVariable("id") Long reverseAuctionId) {
        return offerService.getOffersByReverseAuctionId(reverseAuctionId);
    }

    @GetMapping("offerer/{id}")
    public List<Offer> getOffersByOffererId(@PathVariable("id") Long offererId) {
        return offerService.getOffersByOffererId(offererId);
    }
}
