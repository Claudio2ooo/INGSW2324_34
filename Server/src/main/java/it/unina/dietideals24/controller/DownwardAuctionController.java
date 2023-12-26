package it.unina.dietideals24.controller;

import it.unina.dietideals24.dto.DownwardAuctionDto;
import it.unina.dietideals24.enumeration.CategoryEnum;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.service.interfaces.IDietiUserService;
import it.unina.dietideals24.service.interfaces.IDownwardAuctionService;
import it.unina.dietideals24.service.interfaces.IImageService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/downward-auctions")
public class DownwardAuctionController {

    @Autowired
    @Qualifier("mainDownwardAuctionService")
    private IDownwardAuctionService downwardAuctionService;

    @Autowired
    @Qualifier("mainDietiUserService")
    private IDietiUserService dietiUserService;

    @Autowired
    @Qualifier("locallyStoreImageService")
    private IImageService imageService;
    private static String DOWNWARD_AUCTION_IMAGE_DIRECTORY = "images/downward_auction";

    @GetMapping
    public List<DownwardAuction> getDownwardAuctions() {
        return downwardAuctionService.getDownwardAuctions();
    }

    @GetMapping("{id}")
    public DownwardAuction getDownwardAuctionById(@PathVariable("id") Long id) {
        return downwardAuctionService.getDownwardAuctionById(id);
    }

    @GetMapping("owner/{id}")
    public List<DownwardAuction> getDownwardAuctionsByOwner(@PathVariable("id") Long ownerId) {
        return downwardAuctionService.getDownwardAuctionsByOwner(ownerId);
    }

    @GetMapping("{category}")
    public List<DownwardAuction> getDownwardAuctionsByCategory(@PathVariable("category") CategoryEnum category) {
        return downwardAuctionService.getDownwardAuctionsByCategory(category);
    }

    @PostMapping("/create")
    public ResponseEntity<DownwardAuction> createDownwardAuction(@RequestBody DownwardAuctionDto downwardAuctionDto) throws BadRequestException {
        if(dietiUserService.existsById(downwardAuctionDto.getOwnerId())){
            DietiUser owner = dietiUserService.getUserById(downwardAuctionDto.getOwnerId());
            DownwardAuction createdDownwardAuction = downwardAuctionService.save(downwardAuctionDto, owner);

            return ResponseEntity.ok(createdDownwardAuction);
        } else {
            throw new BadRequestException("User not found");
        }
    }

    @PostMapping("{id}/image")
    public ResponseEntity<String> updateDownwardAuctionImage(@PathVariable Long id, @RequestParam("image")MultipartFile image){
        if(downwardAuctionService.existsById(id)){
            try{
                imageService.saveImage(DOWNWARD_AUCTION_IMAGE_DIRECTORY, id, image);
                downwardAuctionService.linkImage(DOWNWARD_AUCTION_IMAGE_DIRECTORY, id);
                return new ResponseEntity<>("Aucton image updated!", HttpStatus.OK);
            } catch (IOException e){
                return new ResponseEntity<>("Could not update image", HttpStatus.BAD_REQUEST);
            }
        }
        else
            return new ResponseEntity<>("Auction not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public void deleteDownwardAuction(@PathVariable("id") Long id) {
        downwardAuctionService.deleteDownwardAuctionById(id);
        //TODO implementare metodo che manda notifica a venditore per asta al ribasso fallita
    }
}
