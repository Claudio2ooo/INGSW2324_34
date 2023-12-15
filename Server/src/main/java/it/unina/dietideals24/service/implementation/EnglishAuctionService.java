package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.repository.IEnglishAuctionRepository;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mainEnglishAuctionService")
public class EnglishAuctionService implements IEnglishAuctionService {
    @Autowired
    private IEnglishAuctionRepository englishAuctionRepository;

    @Override
    public List<EnglishAuction> getEnglishAuctions() {
        return englishAuctionRepository.findAll();
    }

    @Override
    public EnglishAuction getEnglishAuctionById(Long id) {
        Optional<EnglishAuction> englishAuctionOptional = englishAuctionRepository.findById(id);
        if(englishAuctionOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EnglishAuction not found");
        }
        return englishAuctionOptional.get();
    }

    @Override
    public List<EnglishAuction> getEnglishAuctionsByOwner(Long ownerId) {
        return englishAuctionRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<EnglishAuction> getEnglishAuctionsByCategory(CategoryEnum category) {
        return englishAuctionRepository.findByCategory(category);
    }

    @Override
    public void deleteEnglishAuctionById(Long id) {
        boolean exists = englishAuctionRepository.existsById(id);
        if(!exists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EnglishAuction not found");
        }
        englishAuctionRepository.deleteById(id);
    }
}
