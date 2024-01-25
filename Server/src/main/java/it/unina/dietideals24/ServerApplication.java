package it.unina.dietideals24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        createNecessaryDirectories();
        SpringApplication.run(ServerApplication.class, args);
    }

    private static void createNecessaryDirectories() {
        new File("images").mkdir();
        new File("images/user").mkdir();
        new File("images/english_auction").mkdir();
        new File("images/downward_auction").mkdir();
    }
}
