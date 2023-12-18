package it.unina.dietideals24.controller;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.model.ReverseAuction;
import it.unina.dietideals24.service.interfaces.IReverseAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reverse-auctions")
public class ReverseAuctionController {

    @Autowired
    @Qualifier("mainReverseAuctionService")
    private IReverseAuctionService reverseAuctionService;

    @GetMapping
    public List<ReverseAuction> getReverseAuctions() {
        return reverseAuctionService.getReverseAuctions();
    }

    @GetMapping("{id}")
    public ReverseAuction getReverseAcutionById(@PathVariable("id") Long id) {
        return reverseAuctionService.getReverseAuctionById(id);
    }

    @GetMapping("owner/{id}")
    public List<ReverseAuction> getReverseAuctionsByOwner(@PathVariable("id") Long ownerId) {
        return reverseAuctionService.getReverseAuctionsByOwner(ownerId);
    }

    @GetMapping("{category}")
    public List<ReverseAuction> getReverseAuctionsByCategory(@PathVariable("category") CategoryEnum category) {
        return reverseAuctionService.getReverseAuctionsByCategory(category);
    }

    @DeleteMapping("{id}")
    public void deleteReverseAuction(@PathVariable("id") Long id) {
        reverseAuctionService.deleteReverseAcutionById(id);
    }
}
