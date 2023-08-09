package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoDemoService {


private final ObjectProvider<MyLogger> logger; // Controller에서 생성된 MyLogger 공유!
                                               // -> @Scope("request")으로 인해, 같은 요청 URL이므로, 같은 스프링 빈(MyLogger)이 반환된다.


    public void logic(String id){

        MyLogger myLogger = this.logger.getObject();

        myLogger.loggin("service id = " + id);

    }

}
