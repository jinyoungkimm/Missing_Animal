package Portfolio.Missing_Animal.restAPI.validation;



import Portfolio.Missing_Animal.APIdto.UpdateRegisterRequest;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UpdateRegisterRequestValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {

        System.out.println("clazz = " +  UpdateRegisterRequest.class.isAssignableFrom(clazz));

        // 타입 검증!
        return UpdateRegisterRequest.class.isAssignableFrom(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        System.out.println("11111111111122222222222222333333333333");

        UpdateRegisterRequest updateRegisterRequest = (UpdateRegisterRequest) target;

        if(updateRegisterRequest.getRegisterId() == null)
            errors.rejectValue("registerId","required","registerId값은 필수값입니다.");

    }
}
