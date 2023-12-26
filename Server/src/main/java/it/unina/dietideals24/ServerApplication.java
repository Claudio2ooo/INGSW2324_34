package it.unina.dietideals24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    /*
    TODO
     sistema di notifiche
     avviare i timer quando viene inserita asta
     aggiornare i prezzi delle aste quando arriva offerta
     gestione immagini
     */
}
