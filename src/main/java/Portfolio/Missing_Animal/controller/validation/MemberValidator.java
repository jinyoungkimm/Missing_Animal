package Portfolio.Missing_Animal.controller.validation;

import Portfolio.Missing_Animal.domainEntity.Member;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class MemberValidator implements Validator { // Validator는 자바 표준이 아닌, Spring이 제공하는 인터페이스!

    @Override
    public boolean supports(Class<?> clazz) {

        // 타입 검증
        return Member.class.isAssignableFrom(clazz);

        // 즉, 매개변수로 넘어오는 clazz의 타입이 Member가 맞냐? or Member의 자식 타입이 맞냐를 검증!


    }

    @Override
    public void validate(Object target, Errors errors) { // Errors는 BindingResult의 인터페이스이다. BindingResult가 매개변수로 넘어 온다.

        Member member = (Member)target; // 타입 캐스팅

        if(!StringUtils.hasText(member.getUsername()))
            errors.rejectValue("username","required");

        if(!StringUtils.hasText(member.getUserId()))
            errors.rejectValue("userId","required");

        if(!StringUtils.hasText(member.getPassword()))
            errors.rejectValue("password","required");

        if(!StringUtils.hasText(member.getPhoneNumber()))
            errors.rejectValue("phoneNumber","required");
        else{

            String phoneNumber = member.getPhoneNumber();
            try{

                Integer i = Integer.parseInt(phoneNumber);

            }
            catch (Exception e){

                errors.rejectValue("phoneNumber","typeMismatch.phoneNumber");

            }

        }
        if(member.getEmail() != null ) {

            if (!StringUtils.hasText(member.getEmail().getFirst()))
                errors.rejectValue("email.first", "required");

            if (!StringUtils.hasText(member.getEmail().getLast()))
                errors.rejectValue("email.last", "required");
        }
        else{

                errors.rejectValue("email.first", "required");
                errors.rejectValue("email.last", "required");

        }

    }
}
