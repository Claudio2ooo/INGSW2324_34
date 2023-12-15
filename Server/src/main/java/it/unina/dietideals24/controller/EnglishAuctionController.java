package it.unina.dietideals24.controller;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/english_auction")
public class EnglishAuctionController {
    @Autowired
    @Qualifier("mainEnglishAuctionService")
    private IEnglishAuctionService englishAuctionService;

    @GetMapping
    public List<EnglishAuction> getEnglishAuctions(){
        return englishAuctionService.getEnglishAuctions();
    }

    @GetMapping
    public EnglishAuction getEnglishAuctionById(@RequestParam Long id){
        return englishAuctionService.getEnglishAuctionById(id);
    }

    @GetMapping
    public List<EnglishAuction> getEnglishAuctionsByOwner(@RequestParam Long ownerId){
        return englishAuctionService.getEnglishAuctionsByOwner(ownerId);
    }

    @GetMapping
    public List<EnglishAuction> getEnglishAuctionsByCategory(@RequestParam CategoryEnum category){
        return englishAuctionService.getEnglishAuctionsByCategory(category);
    }

    @DeleteMapping(path = "{englishAuctionId}")
    public void deleteEnglishAuction(@PathVariable("englishAuctionId") Long id){
        englishAuctionService.deleteEnglishAuctionById(id);
    }
}
