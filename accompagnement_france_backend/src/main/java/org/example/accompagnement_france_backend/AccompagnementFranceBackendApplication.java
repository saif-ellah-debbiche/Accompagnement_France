package org.example.accompagnement_france_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccompagnementFranceBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccompagnementFranceBackendApplication.class, args);
	}

}
