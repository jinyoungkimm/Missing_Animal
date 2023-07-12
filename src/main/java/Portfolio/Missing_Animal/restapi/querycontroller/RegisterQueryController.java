package Portfolio.Missing_Animal.restapi.querycontroller;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.RegisterDto;
import Portfolio.Missing_Animal.restapi.queryrepository.RegisterQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterQueryController {

    private final RegisterQueryRepository registerQueryRepository;


    @GetMapping("")
    List<RegisterDto> getAllRegister(){

        List<Register> allRegisters = registerQueryRepository.findAllRegisters();

        List<RegisterDto> collect = allRegisters.stream().
                map(r -> new RegisterDto(r))
                .collect(toList());

        return collect;

    }

    @GetMapping("/{registerId}/member")
    MemberDto getMemberInfo(@PathVariable("registerId") Long id){

        List<Register> registerWithId = registerQueryRepository.findRegisterWithId(id);

        List<MemberDto> collect = registerWithId.stream().
                map(r -> new MemberDto(r.getMember()))
                .collect(toList());

        return collect.get(0);

    }





}
