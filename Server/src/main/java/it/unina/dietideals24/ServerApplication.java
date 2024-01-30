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
        if (new File("images").mkdir())
            System.out.println("images folder created");
        else
            System.out.println("images folder already exists, skipping...");

        if (new File("images/user").mkdir())
            System.out.println("images/user folder created");
        else
            System.out.println("images/user folder already exists, skipping...");

        if (new File("images/english_auction").mkdir())
            System.out.println("images/english_auction folder created");
        else
            System.out.println("images/english_auction folder already exists, skipping...");

        if (new File("images/downward_auction").mkdir())
            System.out.println("images/downward_auction folder created");
        else
            System.out.println("images/downward_auction folder already exists, skipping...");

    }
}
