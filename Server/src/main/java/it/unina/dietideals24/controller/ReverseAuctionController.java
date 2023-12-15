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
@RequestMapping("/reverse_mapping")
public class ReverseAuctionController {
    @Autowired
    @Qualifier("mainReverseAuctionService")
    private IReverseAuctionService reverseAuctionService;

    @GetMapping
    public List<ReverseAuction> getReverseAuctions(){
        return reverseAuctionService.getReverseAuctions();
    }

    @GetMapping
    public ReverseAuction getReverseAcutionById(@RequestParam Long id){
        return reverseAuctionService.getReverseAuctionById(id);
    }

    @GetMapping
    public List<ReverseAuction> getReverseAuctionsByOwner(@RequestParam Long ownerId){
        return reverseAuctionService.getReverseAuctionsByOwner(ownerId);
    }

    @GetMapping
    public List<ReverseAuction> getReverseAuctionsByCategory(@RequestParam CategoryEnum category){
        return reverseAuctionService.getReverseAuctionsByCategory(category);
    }

    @DeleteMapping(path = "{reverseAuctionId}")
    public void deleteReverseAuction(@PathVariable("reverseAuctionId") Long id){
        reverseAuctionService.deleteReverseAcutionById(id);
    }

}
