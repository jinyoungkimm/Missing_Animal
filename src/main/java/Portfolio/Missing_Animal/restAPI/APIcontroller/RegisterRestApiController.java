package Portfolio.Missing_Animal.restAPI.APIcontroller;

import Portfolio.Missing_Animal.dto.SolvedIncidentDto;
import Portfolio.Missing_Animal.restAPI.APIService.RegisterRestApiService;
import Portfolio.Missing_Animal.APIdto.RegisterRequestDto;
import Portfolio.Missing_Animal.APIdto.RegisterResponseDto;
import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/registerApi")
@RequiredArgsConstructor
public class RegisterRestApiController {

    private final RegisterRestApiService registerService;

    private final MemberService memberService;

    @PostMapping("") // 실종 등록 API
    public RegisterResponseDto registerApi(@RequestBody RegisterRequestDto registerRequestDto) {

        Register register = createRegister(registerRequestDto);

        Member member = register.getMember();

        boolean login = memberService.login(member);

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();
        registerResponseDto.setComplete(false);
        if (login == true) {

            Long saveId = registerService.registerApi(register);
            registerResponseDto.setComplete(true);
            return registerResponseDto;
        }
        else {

            throw new IllegalStateException("ID와 PW를 다시 확인해주세요");

        }
    }

    // 총 등록된 Register 개수와 그 중 해결된 Register 개수를 반환
    @GetMapping("/solvedIncident")
    public SolvedIncidentDto querySolvedIncident( ){


        SolvedIncidentDto solvedIncidentDto = registerService.countAllRegisters();


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
