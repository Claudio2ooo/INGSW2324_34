package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.repository.IEnglishAuctionRepository;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mainEnglishAuctionService")
public class EnglishAuctionService implements IEnglishAuctionService {
    @Autowired
    private IEnglishAuctionRepository englishAuctionRepository;
}
