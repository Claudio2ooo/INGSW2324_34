package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.model.Offer;
import it.unina.dietideals24.repository.IOfferRepository;
import it.unina.dietideals24.service.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("mainOfferService")
public class OfferService implements IOfferService {
    @Autowired
    private IOfferRepository offerRepository;

    @Override
    public List<Offer> getOffersByEnglishAuctionId(Long englishAuctionId) {
        return offerRepository.findByTargetEnglishAuctionId(englishAuctionId);
    }

    @Override
    public List<Offer> getOffersByDownwardAuctionId(Long downwardAuctionId) {
        return offerRepository.findByTargetDownwardAuctionId(downwardAuctionId);
    }

    @Override
    public List<Offer> getOffersByOffererId(Long offererId) {
        return offerRepository.findByOffererId(offererId);
    }

    @Override
    public Offer save(Offer betterOffer) {
        return offerRepository.save(betterOffer);
    }
}
