package it.unina.dietideals24.service.interfaces;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DownwardAuction;

import java.util.List;

public interface IDownwardAuctionService {
    List<DownwardAuction> getDownwardAuctions();

    DownwardAuction getDownwardAuctionById(Long id);

    List<DownwardAuction> getDownwardAuctionsByOwner(Long ownerId);

    List<DownwardAuction> getDownwardAuctionsByCategory(CategoryEnum category);

    void deleteDownwardAuctionById(Long id);
}
