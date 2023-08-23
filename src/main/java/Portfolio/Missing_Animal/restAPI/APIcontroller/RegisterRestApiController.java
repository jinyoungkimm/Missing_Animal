package Portfolio.Missing_Animal.restAPI.APIcontroller;

import Portfolio.Missing_Animal.APIdto.*;
import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.dto.SolvedIncidentDto;
import Portfolio.Missing_Animal.restAPI.APIService.RegisterRestApiService;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import Portfolio.Missing_Animal.restAPI.validation.RegisterRequestDtoValidator;
import Portfolio.Missing_Animal.restAPI.validation.UpdateRegisterRequestValidator;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/registers")
@RequiredArgsConstructor
@LogTrace
public class RegisterRestApiController {

    private final RegisterRestApiService registerRestApiService;

    private final RegisterService registerService;

    private final MemberService memberService;

    private final RegisterRequestDtoValidator registerRequestDtoValidator;
    private final UpdateRegisterRequestValidator updateRegisterRequestValidator;

  /*  @InitBinder
    public void init(WebDataBinder dataBinder){

        dataBinder.addValidators(registerRequestDtoValidator);
        dataBinder.addValidators(updateRegisterRequestValidator);

    }*/



    @PostMapping("/new") // 실종 등록 API
    public Object registerApi(@RequestBody  RegisterRequestDto registerRequestDto, BindingResult bindingResult) {

       registerRequestDtoValidator.validate(registerRequestDto,bindingResult); // 직접 호출

        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();


        Register register = createRegister(registerRequestDto);

        Member member = register.getMember();

        Member login = memberService.login(member);

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();
        registerResponseDto.setComplete(false);
        if (login != null) {

            Long saveId = registerRestApiService.registerApi(register);
            registerResponseDto.setComplete(true);
            return registerResponseDto;
        }
        else {

            throw new IllegalStateException("ID와 PW를 다시 확인해주세요");

        }
    }

    // 실종 등록 내용 [수정] API
    @PostMapping("/edit")
    public Object updateRegister(@RequestBody  UpdateRegisterRequest updateRegisterRequest, BindingResult bindingResult){

        updateRegisterRequestValidator.validate(updateRegisterRequest,bindingResult); // 직접 호출


        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();


        Register register = new Register();

        register.setAnimalName(updateRegisterRequest.getAnimalName());
        register.setAnimalAge(updateRegisterRequest.getAnimalAge());
        register.setAnimalSex(updateRegisterRequest.getAnimalSex());
        register.setAnimalWeight(updateRegisterRequest.getAnimalWeight());
        register.setAnimalVariety(updateRegisterRequest.getAnimalVariety());

        register.setRegisterStatus(updateRegisterRequest.getRegisterStatus());
        register.setReportedStatus(updateRegisterRequest.getReportedStatus());


        Long registerId = updateRegisterRequest.getRegisterId();
        Long updateId = registerService.updateForm(registerId, register);

        if(updateId != null){

            UpdateRegisterResponse updateRegisterResponse = new UpdateRegisterResponse(registerId, true);
            return updateRegisterResponse;

        }
        else{

            UpdateRegisterResponse updateRegisterResponse = new UpdateRegisterResponse(registerId, false);
            return updateRegisterResponse;

        }

    }
    // 총 등록된 Register 개수와 그 중 해결된 Register 개수를 반환
    @GetMapping("/solvedIncident")
    public SolvedIncidentDto querySolvedIncident( ){


        SolvedIncidentDto solvedIncidentDto = registerRestApiService.countAllRegisters();


        return solvedIncidentDto;

    }
    static public Register createRegister(RegisterRequestDto registerRequestDto){

        Member member = new Member();
        member.setUserId(registerRequestDto.getUserId());
        member.setPassword(registerRequestDto.getPassword());

        MissingAddress missingAddress = new MissingAddress();
        missingAddress.setZipcode(registerRequestDto.getZipcode());
        missingAddress.setPrefecture(registerRequestDto.getPrefecture());
        missingAddress.setCityName(registerRequestDto.getCityName());
        missingAddress.setDong(registerRequestDto.getDong());
        missingAddress.setGu(registerRequestDto.getGu());
        missingAddress.setStreetName(registerRequestDto.getStreetName());
        missingAddress.setStreetNumber(registerRequestDto.getStreetNumber());

        Register register = new Register();
        register.setMember(member);
        register.setMissingAddress(missingAddress);

        register.setAnimalName(registerRequestDto.getAnimalName());
        register.setAnimalAge(register.getAnimalAge());
        register.setAnimalSex(register.getAnimalSex());
        register.setAnimalWeight(registerRequestDto.getAnimalWeight());
        register.setAnimalVariety(registerRequestDto.getAnimalVariety());

        register.setRegisterDate(LocalDateTime.now());
        register.setRegisterStatus(RegisterStatus.NOT_SOLVED);
        register.setReportedStatus(ReportedStatus.NO);

        return register;
    }

}
