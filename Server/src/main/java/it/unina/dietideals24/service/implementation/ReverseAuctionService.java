package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.ReverseAuction;
import it.unina.dietideals24.repository.IReverseAuctionRepository;
import it.unina.dietideals24.service.interfaces.IReverseAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mainReverseAuctionService")
public class ReverseAuctionService implements IReverseAuctionService {
    @Autowired
    private IReverseAuctionRepository reverseAuctionRepository;


    @Override
    public List<ReverseAuction> getReverseAuctions() {
        return reverseAuctionRepository.findAll();
    }

    @Override
    public ReverseAuction getReverseAuctionById(Long id) {
        Optional<ReverseAuction> reverseAuctionOptional = reverseAuctionRepository.findById(id);
        if(reverseAuctionOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ReverseAuction not found");
        }
        return reverseAuctionOptional.get();
    }

    @Override
    public List<ReverseAuction> getReverseAuctionsByOwner(Long ownerId) {
        return reverseAuctionRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<ReverseAuction> getReverseAuctionsByCategory(CategoryEnum category) {
        return reverseAuctionRepository.findByCategory(category);
    }

    @Override
    public void deleteReverseAcutionById(Long id) {
        boolean exists = reverseAuctionRepository.existsById(id);
        if(!exists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ReverseAuction not found");
        }
        reverseAuctionRepository.deleteById(id);
    }
}
