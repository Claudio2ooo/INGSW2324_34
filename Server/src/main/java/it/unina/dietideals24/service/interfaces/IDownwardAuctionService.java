package it.unina.dietideals24.service.interfaces;

import it.unina.dietideals24.dto.DownwardAuctionDto;
import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.model.DownwardAuction;

import java.util.List;

public interface IDownwardAuctionService {
    List<DownwardAuction> getDownwardAuctions();

    DownwardAuction getDownwardAuctionById(Long id);

    List<DownwardAuction> getDownwardAuctionsByOwner(Long ownerId);

    List<DownwardAuction> getDownwardAuctionsByCategory(CategoryEnum category);

    void deleteDownwardAuctionById(Long id);

    DownwardAuction save(DownwardAuctionDto downwardAuctionDto, DietiUser owner);

    boolean existsById(Long id);

    void linkImage(String downwardAuctionImageDirectory, Long id);
}
