package Portfolio.Missing_Animal.controller;

import Portfolio.Missing_Animal.MyLogger;
import Portfolio.Missing_Animal.service.LogoDemoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogoDemoService logoDemoService; // @Scope()의 디폴트 값이 singleTon이므로 하나만 생성.
    private final ObjectProvider<MyLogger> logger; // http 요청이 와야 지만 객체 생성.
                                                   // -> 고로, 애플리케이션 실행 시점에서 의존 관계인 MyLooger 생성이 안 돼서, 에러가 뜬다.
                                                   // -> ObjectProvider을 사용하면, 아래에서 "logger.getObject()"가 실행되기 까지
                                                    // MyLogger 의존 관계 주입을 미룬다.
    @RequestMapping("/log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){

        String requestURL = request.getRequestURL().toString();

        MyLogger myLogger = logger.getObject(); // 이 시점에서 MyLogger이 지연 생성됨.
                                                // 이 코드 시점에는 HTTP 요청이 진행 중이으로, MyLogger이 정상 생성됨.
        myLogger.setRequestURL(requestURL); // 외부에서 requestURL 주입!

        myLogger.loggin("controller test");
        logoDemoService.logic("testId");

        return "ok";


    }


}
