package it.unina.dietideals24.controller;

import it.unina.dietideals24.service.interfaces.IReverseAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reverse_mapping")
public class ReverseAuctionController {
    @Autowired
    @Qualifier("mainReverseAuctionService")
    private IReverseAuctionService reverseAuctionService;


}
