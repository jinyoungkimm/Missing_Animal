package Portfolio.Missing_Animal.restAPI.validation;

import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.APIdto.UpdateRegisterRequest;
import Portfolio.Missing_Animal.APIdto.UpdateReportRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UpdateReportRequestValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {

        return UpdateReportRequest.class.isAssignableFrom(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {



        UpdateReportRequest updateReportRequest = (UpdateReportRequest) target;

        if(updateReportRequest.getReportId() == null)
            errors.rejectValue("reportId","required","reportId는 필수 입력값입니다.");


        if(updateReportRequest.getFindedAddress() == null){

            errors.rejectValue("findedAddress","required","findedAddress는 필수 입력값입니다.");

        }

        else{

            if(!StringUtils.hasText(updateReportRequest.getFindedAddress().getZipcode()))
            {
                errors.rejectValue("findedAddress.zipcode","required","zipcode는 필수 입력값입니다.");
            }
            else{

                try{

                    String zipcode = updateReportRequest.getFindedAddress().getZipcode();
                    Integer i = Integer.parseInt(zipcode);

                }
                catch (Exception e){

                    errors.rejectValue("findedAddress.zipcode","typeMismatch","zipcode에는 숫자만 입력 가능");

                }

            }

            if(!StringUtils.hasText(updateReportRequest.getFindedAddress().getStreetAdr()))
                errors.rejectValue("findedAddress.streetAdr","required","streetAdr은 필수 입력값입니다");


        }


    }
}
