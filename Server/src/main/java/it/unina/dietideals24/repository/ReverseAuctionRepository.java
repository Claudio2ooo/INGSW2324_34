package it.unina.dietideals24.repository;

import it.unina.dietideals24.model.ReverseAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReverseAuctionRepository extends JpaRepository<ReverseAuction, Long> {
}
