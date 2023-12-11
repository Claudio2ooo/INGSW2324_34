package it.unina.dietideals24.config;

import org.springframework.context.annotation.Configuration;

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
