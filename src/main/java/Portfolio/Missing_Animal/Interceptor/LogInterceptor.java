package Portfolio.Missing_Animal.Interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String LOG_ID = "logId";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID,uuid); // LogInterceptore은 싱글톤처럼 동작하기 때문에, 이렇게 set을 해 두면
                                           // postHandle,afterCompletion()에서 getAttribute(LOG_ID)로 꺼내서 사용 가능!

        //@RequestMapping: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        if(handler instanceof HandlerMethod){

            HandlerMethod handlerMethod = (HandlerMethod) handler;

        }

        log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);

        return true; // true가 아니면 절대로 컨트롤러를 호출하지 않고 끝낸다.

    }

    @Override // 예외가 발생하면 postHandle은 호출이 안 된다.
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("postHandle [{}]",modelAndView);

    }

    @Override // 예외가 발생하든 안 하든 항상 호출된다. 고로, 종료 로그는 항상 afterCompletion()에서 구현을 하자!!!
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


        String requestURI = request.getRequestURI();

        String logId = (String) request.getAttribute(LOG_ID);

        // 종료에 관한 로그는 항상 afterCompletion()에서!!!!
        log.info("REPONSE [{}][{}]",logId,requestURI);


        // 예외에 대한 처리는 반드시 항상 호출되는 afterCompletion()에서 해주자!
        if(ex != null){
            log.error("컨트롤러에서 예외가 발생함",ex);
        }


    }
}
