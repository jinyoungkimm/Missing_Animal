package Portfolio.Missing_Animal.restAPI.validation;

import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginRequestDtoValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {


        // 타입 검증!
        return LoginRequestDto.class.isAssignableFrom(clazz);

    }

    // 로직 검증
    @Override
    public void validate(Object target, Errors errors) {


        LoginRequestDto  loginRequestDto = (LoginRequestDto) target;

        if(!StringUtils.hasText(loginRequestDto.getUserId()))
                errors.rejectValue("userId","required","userId는 필수 입력값입니다.");

        if(!StringUtils.hasText(loginRequestDto.getPassword()))
            errors.rejectValue("password","required","password는 필수 입력값입니다.");


    }


}
