package Portfolio.Missing_Animal.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

    /**
     * 컨트롤러(HomeController :: "/") 호출 직전에
     * @Login + Member member가 있으면, 스프링 인터셉터 단계에서 [자동]으로 해당 세션의 객체를 찾아서 반환해주는 인터셉터 기능을 구현해 보자!
     */

}
