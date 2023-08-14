package Portfolio.Missing_Animal.restAPI.validation;

import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.APIdto.RegisterRequestDto;
import Portfolio.Missing_Animal.APIdto.UpdateMemberRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class UpdateMemberRequestValidtor implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        // 타입 검증!
        return UpdateMemberRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


        UpdateMemberRequest updateMemberRequest = (UpdateMemberRequest) target;




    }
}
