package it.unina.dietideals24.repository;

import it.unina.dietideals24.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByEnglishAuctionId(Long englishAuctionId);

    List<Offer> findByReverseAuctionId(Long reverseAuctionId);

    List<Offer> findByOffererId(Long offererId);
}
