package it.unina.dietideals24.service.interfaces;

import it.unina.dietideals24.model.Offer;

import java.util.List;

public interface IOfferService {

    List<Offer> getOffersByEnglishAuctionId(Long englishAuctionId);

    List<Offer> getOffersByDownwardAuctionId(Long downwardAuctionId);

    List<Offer> getOffersByOffererId(Long offererId);
}
