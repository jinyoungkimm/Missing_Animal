package Portfolio.Missing_Animal.restAPI.validation;


import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.APIdto.ReportRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReportRequestDtoValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {


        // 타입 검증!
        return ReportRequestDto.class.isAssignableFrom(clazz);


    }

    @Override
    public void validate(Object target, Errors errors) {

        ReportRequestDto reportRequestDto = (ReportRequestDto)target;

        if(!StringUtils.hasText(reportRequestDto.getUserId()))
            errors.rejectValue("userId","required","userId는 필수 입력값입니다.");

        if(!StringUtils.hasText(reportRequestDto.getPassword()))
            errors.rejectValue("password","required","password는 필수 입력값입니다.");


        if(reportRequestDto.getRegisterId() == null)
            errors.rejectValue("registerId","required","registerId는 필수 입력값입니다.");



        if(reportRequestDto.getFindedAddress() == null){

            errors.rejectValue("findedAddress","required","findedAddress는 필수 입력값입니다.");

        }

        else{

            if(!StringUtils.hasText(reportRequestDto.getFindedAddress().getZipcode()))
            {
                errors.rejectValue("findedAddress.zipcode","required","zipcode는 필수 입력값입니다.");
            }
            else{

                try{

                    String zipcode = reportRequestDto.getFindedAddress().getZipcode();
                    Integer i = Integer.parseInt(zipcode);

                }
                catch (Exception e){

                    errors.rejectValue("findedAddress.zipcode","typeMismatch","zipcode에는 숫자만 입력 가능");

                }

            }

            if(!StringUtils.hasText(reportRequestDto.getFindedAddress().getStreetAdr()))
                errors.rejectValue("findedAddress.streetAdr","required","streetAdr은 필수 입력값입니다");


        }



    }
}
