package it.unina.dietideals24.repository;

import it.unina.dietideals24.model.EnglishAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnglishAuctionRepository extends JpaRepository<EnglishAuction, Long> {
}
