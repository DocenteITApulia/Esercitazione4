package it.apulia.Esercitazione4.apuliaAirport;

import it.apulia.Esercitazione4.apuliaAirport.utils.UtilService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AirportConfig {
    @Bean
    CommandLineRunner commandLineRunner(UtilService utilService) {
        return args -> {
            utilService.resetAll();
            utilService.init();


        };

    }
}
