package Portfolio.Missing_Animal;

import Portfolio.Missing_Animal.Aspect.LogTraceAspect;
import Portfolio.Missing_Animal.propertiesWithJava.StoragePropertiesForReport;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForRegister;
import Portfolio.Missing_Animal.propertiesWithJava.StoragePropertiesForRegister;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForReport;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties({StoragePropertiesForRegister.class,StoragePropertiesForReport.class})
public class MissingAnimalApplication {


	public static void main(String[] args) {

		SpringApplication.run(MissingAnimalApplication.class, args);

	}

	@Bean
	CommandLineRunner init1(StorageServiceForRegister storageService) {
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

	@Bean
	LogTraceAspect logTraceAspect(){ // 로그 트레이스를 빈 등록

		return new LogTraceAspect();

	}

	/**
	 * HttpExchangeRepository 인터페이스 : HTTP 요청/응답의 과거 기록을 제공하는 Actuator Endpoints이다.
	 * -> HttpExchangeRepository 구현체를 빈 등록 하지 않으면 Endpoint가 활성화 되지 않는다.
	 * 다행히도 스프링 boot는 기본으로 HttpExchangeRepository 구현체인 InMemoryHttpExchangeRepository를 제공하므로
	 * 빈 등록만 해주면 된다.
	 * InMemoryHttpExchangeRepository 구현체 : 최대 100개의 http 요청에 대한 과거 기록만을 제공한ㄷ.ㅏ
	 * 100개가 넘어 가면 과거 요청을 삭제를 한다.
	 * (setCapacity()로 최대 요청 수 변경 가능)
	 * -> 참고로 이 기능은 매우 단순한 기능이며 제한이 ㅁ낳기 때문에 개발 단계에서만 사용하고, 실제 운영 서비스에서는 모니터링 툴이나, 핀 포인트, Zipkin 같은 다른
	 * 기술을 많이 사용한다고 한다.
	 *
	 * Actuator 사용 시 주의 사항 : Actuator는 애플리케이션 내부의 정보를 너~무 많이 노출한다.
	 * 그래서 외부 인터넷 망에서 Actuator의 Endpoint를 공개하는 것은 보안 상 좋은 방법이 아니다.
	 * -> http://localhost:[9292]와 같이 Port Number를 다르게 설정하여, 외부에서 8080는 접근 가능하도록 해 놓고,
	 * 외부에서 9292 port에 대해서는 접근을 막아 놓자!
	 *
	 */
	@Bean
	public InMemoryHttpExchangeRepository httpExchangeRepository(){
		return new InMemoryHttpExchangeRepository();
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
