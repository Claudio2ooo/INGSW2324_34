package it.unina.dietideals24.controller;

import it.unina.dietideals24.service.EnglishAuctionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/english_auction")
public class EnglishAuctionController {
    private final EnglishAuctionService englishAuctionService;

   // @Autowired
    public EnglishAuctionController(EnglishAuctionService englishAuctionService){
        this.englishAuctionService = englishAuctionService;
    }

}
