package it.unina.dietideals24.controller;

import it.unina.dietideals24.service.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping(
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getImage(@RequestParam("i") String url) {
        return imageService.getImage(url);
    }
}
