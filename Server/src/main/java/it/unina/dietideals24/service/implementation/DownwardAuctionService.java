package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.repository.IDownwardAuctionRepository;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public DownwardAuction getDownwardAuctionById(Long id) {
        Optional<DownwardAuction> downwardAuctionOptional = downwardAuctionRepository.findById(id);
        if(downwardAuctionOptional.isEmpty()){
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
        if(!exists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DownwardAuction not found");
        }
        downwardAuctionRepository.deleteById(id);
    }
}
