package it.unina.dietideals24.controller;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/downward-auctions")
public class DownwardAuctionController {

    @Autowired
    @Qualifier("mainDownwardAuctionService")
    private IDownwardAuctionService downwardAuctionService;

    @GetMapping
    public List<DownwardAuction> getDownwardAuctions() {
        return downwardAuctionService.getDownwardAuctions();
    }

    @GetMapping("{id}")
    public DownwardAuction getDownwardAuctionById(@PathVariable("id") Long id) {
        return downwardAuctionService.getDownwardAuctionById(id);
    }

    @GetMapping("owner/{id}")
    public List<DownwardAuction> getDownwardAuctionsByOwner(@PathVariable("id") Long ownerId) {
        return downwardAuctionService.getDownwardAuctionsByOwner(ownerId);
    }

    @GetMapping("{category}")
    public List<DownwardAuction> getDownwardAuctionsByCategory(@PathVariable("category") CategoryEnum category) {
        return downwardAuctionService.getDownwardAuctionsByCategory(category);
    }

    @DeleteMapping("{id}")
    public void deleteDownwardAuction(@PathVariable("id") Long id) {
        downwardAuctionService.deleteDownwardAuctionById(id);
        //TODO implementare metodo che manda notifica a venditore per asta al ribasso fallita
    }
}
