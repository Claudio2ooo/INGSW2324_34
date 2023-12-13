package it.unina.dietideals24.service;

import it.unina.dietideals24.repository.EnglishAuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnglishAuctionService {
    private final EnglishAuctionRepository englishAuctionRepository;

   // @Autowired
    public EnglishAuctionService(EnglishAuctionRepository englishAuctionRepository){
        this.englishAuctionRepository = englishAuctionRepository;
    }
}
