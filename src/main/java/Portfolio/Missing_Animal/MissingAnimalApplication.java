package Portfolio.Missing_Animal;

import Portfolio.Missing_Animal.service.serviceinterface.StorageService;
import Portfolio.Missing_Animal.propertiesWithJava.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MissingAnimalApplication {

	public static void main(String[] args) {

		SpringApplication.run(MissingAnimalApplication.class, args);

	}

	@Bean
	CommandLineRunner init1(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Bean
	CommandLineRunner init2(InitMissingAddress initMissingAddress) {
		return (args) -> {

			initMissingAddress.init();
		};
	}

}
