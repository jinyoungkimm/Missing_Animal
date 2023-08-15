package Portfolio.Missing_Animal.configuration;


import Portfolio.Missing_Animal.Interceptor.LogInterceptor;
import Portfolio.Missing_Animal.Interceptor.LoginInterceptor;
import Portfolio.Missing_Animal.Interceptor.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     * 향후에 로그인에 대한 정책이 바뀌어도, 이 부분만 수정/추가하면 된다.
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(new LogInterceptor())

                .order(1) // 첫번째로 호출되는 스프링 인터셉터(LogInterceptor)

                .addPathPatterns("/**")

                .excludePathPatterns("/css/**", "/*.ico", "/error");


        registry.addInterceptor(new LoginInterceptor())

                .order(2) // 두 번째로 호출되는 스프링 인터셉터

                .addPathPatterns("/**")

                .excludePathPatterns(
                        "/", "/member/join", "/member/login", "/logout",
                        "/css/**", "/*.ico", "/error"
                );


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {


        resolvers.add(new LoginMemberArgumentResolver());



    }

}
