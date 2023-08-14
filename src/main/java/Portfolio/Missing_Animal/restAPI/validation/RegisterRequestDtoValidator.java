package Portfolio.Missing_Animal.restAPI.validation;

import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.APIdto.MemberRequestDto;
import Portfolio.Missing_Animal.APIdto.RegisterRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class RegisterRequestDtoValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {

        System.out.println("RegisterRequestDto.class.isAssignableFrom(clazz) = " + RegisterRequestDto.class.isAssignableFrom(clazz));



        // 타입 검증!
        return RegisterRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegisterRequestDto registerRequestDto = (RegisterRequestDto) target;


        System.out.println("registerRequestDto = " + registerRequestDto);


        if(!StringUtils.hasText(registerRequestDto.getUserId()))
            errors.rejectValue("userId","required","userId는 필수 입력값입니다.");
        if(!StringUtils.hasText(registerRequestDto.getPassword()))
            errors.rejectValue("password","required","password는 필수 입력값입니다.");

        if(!StringUtils.hasText(registerRequestDto.getAnimalName()))
            errors.rejectValue("animalName","required","animalName는 필수 입력값입니다.");


        if(!StringUtils.hasText(registerRequestDto.getZipcode()))
        {
            errors.rejectValue("zipcode","required","zipcode는 필수 입력값입니다.");

        }
        else{

            try{

                String zipcode = registerRequestDto.getZipcode();
                Integer i = Integer.parseInt(zipcode);

            }catch (Exception e){

                errors.rejectValue("zipcode","typeMismatch","숫자만 입력이 가능");

            }
        }

        if(StringUtils.hasText(registerRequestDto.getPrefecture())){

            String prefecture = registerRequestDto.getPrefecture();
            if(prefecture.charAt(prefecture.length()-1) != '도'){

                errors.rejectValue("prefecture","typeMismatch","끝이 [~도]로 끝나야 합니다. ex) 충청북[도]");

            }

        }

        if(StringUtils.hasText(registerRequestDto.getCityName())){

            String cityName = registerRequestDto.getCityName();
            if(cityName.charAt(cityName.length()-1) != '시' && cityName.charAt(cityName.length()-1) != '군' && cityName.charAt(cityName.length()-1) != '면' ){

                errors.rejectValue("cityName","typeMismatch","끝이 [시/군/면]로 끝나야 합니다. ex) 부산[시],함안[군], 일광[면]");

            }

        }

        if(StringUtils.hasText(registerRequestDto.getGu())){

            String Gu = registerRequestDto.getGu();
            if(Gu.charAt(Gu.length()-1) != '구'){

                errors.rejectValue("Gu","typeMismatch","끝이 [~구]로 끝나야 합니다. ex) 해운대[구]");

            }

        }

        if(StringUtils.hasText(registerRequestDto.getDong())){

            String dong = registerRequestDto.getDong();
            if(dong.charAt(dong.length()-1) != '읍' && dong.charAt(dong.length()-1) != '면' && dong.charAt(dong.length()-1) != '동'){

                errors.rejectValue("Dong","typeMismatch","끝이 [읍/면/동]로 끝나야 합니다.");

            }

        }

        if(StringUtils.hasText(registerRequestDto.getStreetName())){

            String streetName = registerRequestDto.getStreetName();
            if(streetName.charAt(streetName.length()-1) != '길' && streetName.charAt(streetName.length()-1) != '로'){

                errors.rejectValue("streetName","typeMismatch","끝이 [~길/~로]로 끝나야 합니다. ex)세실[로],해운대[로]");

            }

        }

        if(StringUtils.hasText(registerRequestDto.getStreetNumber())){

            try {
                String streetNumber = registerRequestDto.getStreetNumber();
                Integer i = Integer.parseInt(streetNumber);
            }
            catch (Exception e){

                errors.rejectValue("streetNumber","typeMismatch","숫자만 입력 가능");
            }


        }

    }
}
