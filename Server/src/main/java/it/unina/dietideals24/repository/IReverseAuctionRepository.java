package it.unina.dietideals24.repository;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.ReverseAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReverseAuctionRepository extends JpaRepository<ReverseAuction, Long> {
    List<ReverseAuction> findByOwnerId(Long ownerId);

    List<ReverseAuction> findByCategory(CategoryEnum category);
}
