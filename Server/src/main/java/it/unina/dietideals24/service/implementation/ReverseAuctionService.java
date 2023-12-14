package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.repository.IReverseAuctionRepository;
import it.unina.dietideals24.service.interfaces.IReverseAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mainReverseAuctionService")
public class ReverseAuctionService implements IReverseAuctionService {
    @Autowired
    private IReverseAuctionRepository reverseAuctionRepository;


}
