package it.unina.dietideals24.controller;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/english-auctions")
public class EnglishAuctionController {

    @Autowired
    @Qualifier("mainEnglishAuctionService")
    private IEnglishAuctionService englishAuctionService;

    @GetMapping
    public List<EnglishAuction> getEnglishAuctions() {
        return englishAuctionService.getEnglishAuctions();
    }

    @GetMapping("{id}")
    public EnglishAuction getEnglishAuctionById(@PathVariable("id") Long id) {
        return englishAuctionService.getEnglishAuctionById(id);
    }

    @GetMapping("owner/{id}")
    public List<EnglishAuction> getEnglishAuctionsByOwner(@PathVariable("id") Long ownerId) {
        return englishAuctionService.getEnglishAuctionsByOwner(ownerId);
    }

    @GetMapping("{category}")
    public List<EnglishAuction> getEnglishAuctionsByCategory(@PathVariable("category") CategoryEnum category) {
        return englishAuctionService.getEnglishAuctionsByCategory(category);
    }

    @DeleteMapping("{id}")
    public void deleteEnglishAuction(@PathVariable("id") Long id) {
        englishAuctionService.deleteEnglishAuctionById(id);
    }
}
