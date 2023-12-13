package it.unina.dietideals24.service;

import it.unina.dietideals24.repository.ReverseAuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReverseAuctionService {
    private final ReverseAuctionRepository reverseAuctionRepository;

  //  @Autowired
    public ReverseAuctionService(ReverseAuctionRepository reverseAuctionRepository){
        this.reverseAuctionRepository = reverseAuctionRepository;
    }
}
