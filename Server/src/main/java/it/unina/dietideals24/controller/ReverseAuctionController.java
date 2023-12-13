package it.unina.dietideals24.controller;

import it.unina.dietideals24.service.ReverseAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reverse_mapping")
public class ReverseAuctionController {
    private final ReverseAuctionService reverseAuctionService;

  //  @Autowired
    public ReverseAuctionController(ReverseAuctionService reverseAuctionService){
        this.reverseAuctionService = reverseAuctionService;
    }
}
