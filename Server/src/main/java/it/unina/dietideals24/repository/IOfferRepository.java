package it.unina.dietideals24.repository;

import it.unina.dietideals24.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, Long> {
}
