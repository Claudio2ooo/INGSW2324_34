package it.unina.dietideals24.repository;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DownwardAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDownwardAuctionRepository extends JpaRepository<DownwardAuction, Long> {
    List<DownwardAuction> findByOwnerId(Long ownerId);

    List<DownwardAuction> findByCategory(CategoryEnum category);
}