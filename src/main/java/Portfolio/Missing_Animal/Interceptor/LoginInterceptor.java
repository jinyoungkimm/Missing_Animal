package Portfolio.Missing_Animal.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final String SESSION_ID="session Id";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String requestURI = request.getRequestURI();

        log.info("로그인 여부 체크 시작 {}",requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SESSION_ID) == null){


            log.info("로그인 되지 않은 사용자의 접근");

            // requestURL에는 접근하려 하였으나, 로그인이 안된 url 주소이다.
            // login 페이지로 리다이렉트시켜서, 로그인이 완료되면 처음에 접근하려 하였던 주소로 이동시켜줘야 한다.
            response.sendRedirect("/member/login?redirectURL="+requestURI); // 이 부분을 처리하는 로직을 컨트롤러에서 추가 개발헤야 함

            return false; // 절대 컨트롤러를 호출하지 않으며, 사용자(웹 브라우저)에게 login 페이지로 리다이렉트 시킨다.

        }

        return true;
    }


}
