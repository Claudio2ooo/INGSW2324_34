package it.unina.dietideals24.api.config;

import it.unina.dietideals24.api.model.DietiUser;
import it.unina.dietideals24.api.repository.DietiUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DietiUserConfig {

    /*@Bean
    CommandLineRunner commandLineRunner(DietiUserRepository dietiUserRepository){
        return args -> {
            DietiUser mario = new DietiUser(
                    "Mario",
                    "Rossi",
                    "mrossi@gmail.com",
                    "password"
            );

            DietiUser peppe = new DietiUser(
                    "Peppe",
                    "Rossi",
                    "prossi@gmail.com",
                    "password"
            );

            dietiUserRepository.saveAll(List.of(mario, peppe));
        };
    }*/
}
