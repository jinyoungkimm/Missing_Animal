package Portfolio.Missing_Animal;

import Portfolio.Missing_Animal.propertiesWithJava.StoragePropertiesForReport;
import Portfolio.Missing_Animal.service.serviceinterface.StorageService;
import Portfolio.Missing_Animal.propertiesWithJava.StorageProperties;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForReport;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class,StoragePropertiesForReport.class})
public class MissingAnimalApplication {


	public static void main(String[] args) {

		SpringApplication.run(MissingAnimalApplication.class, args);

	}

	@Bean
	CommandLineRunner init1(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}

	@Bean
	CommandLineRunner init(StorageServiceForReport storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}

	@Bean
	CommandLineRunner init2(InitMissingAddress initMissingAddress) {
		return (args) -> {

			initMissingAddress.init();
		};
	}


/*	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {@Override
		protected void postProcessContext(Context context) {
			SecurityConstraint securityConstraint = new SecurityConstraint();
			securityConstraint.setUserConstraint("CONFIDENTIAL");
			SecurityCollection collection = new SecurityCollection();
			collection.addPattern("/*");
			securityConstraint.addCollection(collection);
			context.addConstraint(securityConstraint);
		}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}*/

}
