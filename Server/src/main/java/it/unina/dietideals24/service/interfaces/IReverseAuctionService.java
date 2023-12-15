package it.unina.dietideals24.service.interfaces;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.ReverseAuction;

import java.util.List;

public interface IReverseAuctionService {
    List<ReverseAuction> getReverseAuctions();

    ReverseAuction getReverseAuctionById(Long id);

    List<ReverseAuction> getReverseAuctionsByOwner(Long ownerId);

    List<ReverseAuction> getReverseAuctionsByCategory(CategoryEnum category);

    void deleteReverseAcutionById(Long id);
}
