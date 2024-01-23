package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.dto.DownwardAuctionDto;
import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.repository.IDownwardAuctionRepository;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mainDownwardAuctionService")
public class DownwardAuctionService implements IDownwardAuctionService {
    @Autowired
    private IDownwardAuctionRepository downwardAuctionRepository;

    @Override
    public List<DownwardAuction> getDownwardAuctions() {
        return downwardAuctionRepository.findAll();
    }

    @Override
    public List<DownwardAuction> getFirst6DownwardAuctions() {
        return downwardAuctionRepository.findFirst6ByOrderById();
    }

    @Override
    public DownwardAuction getDownwardAuctionById(Long id) {
        Optional<DownwardAuction> downwardAuctionOptional = downwardAuctionRepository.findById(id);
        if (downwardAuctionOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DownwardAuction not found");
        }
        return downwardAuctionOptional.get();
    }

    @Override
    public List<DownwardAuction> getDownwardAuctionsByOwner(Long ownerId) {
        return downwardAuctionRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<DownwardAuction> getDownwardAuctionsByCategory(CategoryEnum category) {
        return downwardAuctionRepository.findByCategory(category);
    }

    @Override
    public void deleteDownwardAuctionById(Long id) {
        boolean exists = downwardAuctionRepository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DownwardAuction not found");
        } else {
            DownwardAuction toBeDeleted = downwardAuctionRepository.findById(id).get();
            toBeDeleted.setOwner(null);
            downwardAuctionRepository.delete(toBeDeleted);
        }
    }

    @Override
    public DownwardAuction save(DownwardAuctionDto downwardAuctionDto, DietiUser owner) {
        DownwardAuction downwardAuction = new DownwardAuction();
        downwardAuction.setTitle(downwardAuctionDto.getTitle());
        downwardAuction.setDescription(downwardAuctionDto.getDescription());
        downwardAuction.setCategory(downwardAuctionDto.getCategory());
        downwardAuction.setTimerInMilliseconds(downwardAuctionDto.getTimerInMilliseconds());
        downwardAuction.setCurrentPrice(downwardAuctionDto.getCurrentPrice());
        downwardAuction.setStartingPrice(downwardAuctionDto.getStartingPrice());
        downwardAuction.setDecreaseAmount(downwardAuctionDto.getDecreaseAmount());
        downwardAuction.setMinimumPrice(downwardAuctionDto.getMinimumPrice());
        downwardAuction.setOwner(owner);

        return downwardAuctionRepository.save(downwardAuction);
    }

    @Override
    public boolean existsById(Long id) {
        return downwardAuctionRepository.existsById(id);
    }

    @Override
    public void linkImage(String downwardAuctionImageDirectory, Long id) {
        DownwardAuction downwardAuction = downwardAuctionRepository.findById(id).get();
        downwardAuction.setImageURL(downwardAuctionImageDirectory + File.separatorChar + id);
        downwardAuctionRepository.save(downwardAuction);
    }

    @Override
    public void save(DownwardAuction toBeDecreased) {
        downwardAuctionRepository.save(toBeDecreased);
    }

    @Override
    public List<DownwardAuction> getByKeyword(String keyword) {
        List<DownwardAuction> foundAuctions = new ArrayList<>();
        foundAuctions.addAll(downwardAuctionRepository.findByTitleContainsIgnoreCase(keyword));
        foundAuctions.addAll(downwardAuctionRepository.findByDescriptionContainsIgnoreCase(keyword));
        return foundAuctions;
    }

    public void decreaseCurrentPrice(Long id) {
        if (existsById(id)) {
            DownwardAuction toBeDecreased = getDownwardAuctionById(id);
            decreaseCurrentPrice(toBeDecreased);
            toBeDecreased.setCreatedAt(new Date(System.currentTimeMillis()));
            save(toBeDecreased);
        }
    }

    private void decreaseCurrentPrice(DownwardAuction toBeDecreased) {
        toBeDecreased.setCurrentPrice(
                toBeDecreased
                        .getCurrentPrice()
                        .subtract(toBeDecreased.getDecreaseAmount()));
    }
}
