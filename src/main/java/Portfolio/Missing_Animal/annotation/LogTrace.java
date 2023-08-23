package Portfolio.Missing_Animal.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTrace {

    /**
     * 스프링 AOP 방식을 이용하여, 로그 출력 기능을 만들 것이다.
     * @LogTrace 애노테이션이 붙은 클래스의 메서드에 대해 Advice를 적용!
     */

}
