package it.unina.dietideals24.springBootControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {

    @GetMapping
    public String foo(){
        return "spring test";
    }
}
