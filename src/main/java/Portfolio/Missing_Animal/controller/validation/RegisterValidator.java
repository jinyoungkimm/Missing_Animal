package Portfolio.Missing_Animal.controller.validation;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        // 타입 검증
        return Register.class.isAssignableFrom(clazz);

        // 즉, 매개변수로 넘어오는 clazz의 타입이 Member가 맞냐? or Register 자식 타입이 맞냐를 검증!


    }

    @Override
    public void validate(Object target, Errors errors) {

        Register register = (Register) target;

         if(!StringUtils.hasText(register.getAnimalName())){
             errors.rejectValue("animalName","required");
        }

        if(!StringUtils.hasText(register.getAnimalSex()))
            errors.rejectValue("animalSex","required");

        if(!StringUtils.hasText(register.getAnimalVariety()))
            errors.rejectValue("animalVariety","required");

        if(!StringUtils.hasText(register.getMissingAddress().getZipcode()))
            errors.rejectValue("missingAddress.zipcode","required");

        if(!StringUtils.hasText(register.getMissingAddress().getStreetName()))
            errors.rejectValue("missingAddress.streetName","required");




    }
}
