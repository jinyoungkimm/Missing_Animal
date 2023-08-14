package Portfolio.Missing_Animal.restAPI.validation;

import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.APIdto.MemberRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class MemberRequestDtoValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {

        // 타입 검증!
        return MemberRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberRequestDto  memberRequestDto = (MemberRequestDto) target;

        if(!StringUtils.hasText(memberRequestDto.getUserName()))
            errors.rejectValue("userName","required","username은 필수 입력값입니다.");

        if(!StringUtils.hasText(memberRequestDto.getUserId()))
            errors.rejectValue("userId","required","userId는 필수 입력값입니다.");

        if(!StringUtils.hasText(memberRequestDto.getPassword()))
            errors.rejectValue("password","required","password는 필수 입력값입니다.");


        if(!StringUtils.hasText(memberRequestDto.getPhoneNumber()))
            errors.rejectValue("phoneNumber","required","phoneNumber는 필수 입력값입니다.");

        else{

            try{

                String phoneNumber = memberRequestDto.getPhoneNumber();
                Integer i = Integer.parseInt(phoneNumber);

            }
            catch (Exception e){

                errors.rejectValue("phoneNumber","typeMismatch","phoneNumber에는 숫자만 입력 가능");
            }



        }

    }
}
