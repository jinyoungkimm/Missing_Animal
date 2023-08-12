package Portfolio.Missing_Animal.controller.validation;

import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReportValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        // 타입 검증
        return Report.class.isAssignableFrom(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        Report report = (Report) target;

        if(!StringUtils.hasText(report.getFindedAddress().getZipcode()))
            errors.rejectValue("findedAddress.zipcode","required");

        if(!StringUtils.hasText(report.getFindedAddress().getStreetAdr()))
            errors.rejectValue("findedAddress.streetAdr","required");

    }
}
