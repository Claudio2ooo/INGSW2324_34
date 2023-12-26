package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.service.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Qualifier("locallyStoreImageService")
public class ImageService implements IImageService {

    /**
     * saves an image locally, using the specified directory
     * @param imageDirectory directory for the image
     * @param id id of the item that has the image, used as name for the image
     * @param file representation of the image
     * @throws IOException thrown when the image can't be saved due to "getBytes()" failing
     */
    @Override
    public void saveImage(String imageDirectory, Long id, MultipartFile file) throws IOException {
        StringBuilder fileName = new StringBuilder();
        String path = imageDirectory + "/" + id.toString();
        Path fileNameAndPath = Paths.get(path);
        Files.write(fileNameAndPath, file.getBytes());
    }

    @Override
    public byte[] getImage(String url) throws IOException {
        return getClass().getResourceAsStream(url).readAllBytes();
    }
}
