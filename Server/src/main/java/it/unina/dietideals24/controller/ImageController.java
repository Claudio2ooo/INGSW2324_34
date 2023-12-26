package it.unina.dietideals24.controller;

import it.unina.dietideals24.service.interfaces.IImageService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping("{url}")
    public @ResponseBody byte[] getImage(@PathVariable String url) throws BadRequestException {
        try {
            return imageService.getImage(url);
        } catch (IOException e) {
            throw new BadRequestException("Image not found");
        }
    }
}
