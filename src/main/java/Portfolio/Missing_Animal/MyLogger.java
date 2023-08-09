package Portfolio.Missing_Animal;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request") // http 요청이 들어와야 지만, 빈이 새로 생성되며, 요청이 들어오고 응답이 종료될 때까지 스프링 컨테이너가 관리!
public class MyLogger {


    public String uuid;
    public String requestURL;

    public void setRequestURL(String requestURL){ // request URL은 스프링 빈 생성 시점, 의존 관계 주입시점에서는 알 수가 없다.
        this.requestURL = requestURL;           // -> Controller에서 HttpServlet requeset를 이용하여 주입받자!
    }

    public void loggin(String message){

        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + "[" + message +"]");

    }


    @PostConstruct // http 요청이 올 떄에 초기화 단계에서 호출됨.
    public void init(){

        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);

    }

    @PreDestroy // http 요청이 끝나는 시점에 호출됨.
    public void close(){

        System.out.println("[" + uuid + "] request scope bean close : " + this);


    }

}
