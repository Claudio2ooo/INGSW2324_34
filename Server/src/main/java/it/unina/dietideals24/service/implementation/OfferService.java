package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.repository.IOfferRepository;
import it.unina.dietideals24.service.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mainOfferService")
public class OfferService implements IOfferService {
    @Autowired
    private IOfferRepository offerRepository;
}