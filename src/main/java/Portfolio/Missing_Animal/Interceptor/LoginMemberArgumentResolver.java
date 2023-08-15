package Portfolio.Missing_Animal.Interceptor;


import Portfolio.Missing_Animal.annotation.Login;
import Portfolio.Missing_Animal.domainEntity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String SESSION_ID="session Id";


    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        log.info("supportsParameter 실행");
        boolean hasLoginAnnotation =
                parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType =
                Member.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasMemberType;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }


        return session.getAttribute(SESSION_ID); // 여기서 해당 세션의 객체(Member)를 찾아서 컨트롤러의 매개변수(@Login Member member)
        //에 담아준다.

    }
}
