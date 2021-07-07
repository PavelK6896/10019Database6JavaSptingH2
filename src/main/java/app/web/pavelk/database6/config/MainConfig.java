package app.web.pavelk.database6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class MainConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
