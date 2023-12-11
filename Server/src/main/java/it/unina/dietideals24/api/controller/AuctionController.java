package it.unina.dietideals24.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auction")
public class AuctionController {

    @GetMapping("/")
    public String foo(){
        return "spring test";
    }
}
