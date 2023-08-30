package Portfolio.Missing_Animal.Aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class LogTraceAspect {

    @Before("@within(Portfolio.Missing_Animal.annotation.LogTrace)") // 클래스에 @LogTrace가 붙어 있으면 모든 메서드에 대해 아래의 Advice 적용!
    public void loggin(JoinPoint joinPoint){ // 메서드에 대한 메타 데이터가 담겨 져 온다.


        Object[] args = joinPoint.getArgs();

        log.info("[LogTrace] : {} args={}",joinPoint.getSignature(),args);


    }
}
