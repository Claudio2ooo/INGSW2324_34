package it.unina.dietideals24.controller;

import it.unina.dietideals24.auction_timer.EnglishAuctionTimerController;
import it.unina.dietideals24.dto.EnglishAuctionDto;
import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.service.interfaces.IDietiUserService;
import it.unina.dietideals24.service.interfaces.IEnglishAuctionService;
import it.unina.dietideals24.service.interfaces.IImageService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/english-auctions")
public class EnglishAuctionController {

    @Autowired
    @Qualifier("mainEnglishAuctionService")
    private IEnglishAuctionService englishAuctionService;

    @Autowired
    @Qualifier("mainDietiUserService")
    private IDietiUserService dietiUserService;

    @Autowired
    private EnglishAuctionTimerController englishAuctionTimerController;

    @Autowired
    @Qualifier("locallyStoreImageService")
    private IImageService imageService;
    private static String ENGLISH_AUCTION_IMAGE_DIRECTORY = "english_auction";

    @GetMapping
    public List<EnglishAuction> getEnglishAuctions() {
        return englishAuctionService.getEnglishAuctions();
    }

    @GetMapping("{id}")
    public EnglishAuction getEnglishAuctionById(@PathVariable("id") Long id) {
        return englishAuctionService.getEnglishAuctionById(id);
    }

    @GetMapping("owner/{id}")
    public List<EnglishAuction> getEnglishAuctionsByOwner(@PathVariable("id") Long ownerId) {
        return englishAuctionService.getEnglishAuctionsByOwner(ownerId);
    }

    @GetMapping("/category/{category}")
    public List<EnglishAuction> getEnglishAuctionsByCategory(@PathVariable("category") CategoryEnum category) {
        return englishAuctionService.getEnglishAuctionsByCategory(category);
    }

    @GetMapping("/search/{keyword}")
    public List<EnglishAuction> getEnglishAuctionsByKeyword(@PathVariable("keyword") String keyword){
        return englishAuctionService.getByKeyword(keyword);
    }

    @PostMapping("/create")
    public ResponseEntity<EnglishAuction> createEnglishAuction(@RequestBody EnglishAuctionDto englishAuctionDto) throws BadRequestException {
        if(dietiUserService.existsById(englishAuctionDto.getOwnerId())){
            DietiUser owner = dietiUserService.getUserById(englishAuctionDto.getOwnerId());
            EnglishAuction createdEnglishAuction = englishAuctionService.save(englishAuctionDto, owner);

            englishAuctionTimerController.startNewTimer(createdEnglishAuction);

            return ResponseEntity.ok(createdEnglishAuction);
        } else {
            throw new BadRequestException("User not found");
        }
    }

    @PostMapping("{id}/image")
    public void uploadEnglishAuctionImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws BadRequestException{
        if(englishAuctionService.existsById(id)){
            try{
                imageService.saveImage(ENGLISH_AUCTION_IMAGE_DIRECTORY, id, image);
                englishAuctionService.linkImage(ENGLISH_AUCTION_IMAGE_DIRECTORY, id);
            } catch (IOException e){
                throw new BadRequestException("Could not upload image");
            }
        }
        else
            throw new BadRequestException("Auction doesn't exist");
    }

    @DeleteMapping("{id}")
    public void deleteEnglishAuction(@PathVariable("id") Long id) {
        englishAuctionService.deleteEnglishAuctionById(id);
    }
}
