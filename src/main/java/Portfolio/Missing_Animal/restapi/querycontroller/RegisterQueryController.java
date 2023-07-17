package Portfolio.Missing_Animal.restapi.querycontroller;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.MissingAddressDto;
import Portfolio.Missing_Animal.dto.RegisterDto;

import Portfolio.Missing_Animal.restapi.queryrepository.RegisterQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterQueryController {

    private final RegisterQueryRepository registerQueryRepository;


    /*@GetMapping("")
    List<RegisterDto> getAllRegister(){

        List<Register> allRegisters = registerQueryRepository.findAllRegisters();

        List<RegisterDto> collect = allRegisters.stream().
                map(r -> new RegisterDto(r))
                .collect(toList());

        return collect;

    }*/

    /*@GetMapping("")
    List<RegisterDto> getRegistersWithPaging(@RequestParam(value="offset" ,defaultValue = "0") int offset,
                                             @RequestParam(value="limit",defaultValue = "5") int limit){


        List<Register> registersWithPaging = registerQueryRepository.findRegistersWithPaging(offset, limit);

        List<RegisterDto> collect = registersWithPaging.stream()
                .map(register -> new RegisterDto(register))
                .collect(toList());

        return collect;

    }*/

    @GetMapping("")
    List<RegisterDto> getRegistersWithPaging(@RequestParam("pageNumber") int pageNumber) {

        int limit = 5;
        int offset = (pageNumber * limit) - limit;


        List<Register> registersWithPaging = registerQueryRepository.findRegistersWithPaging(offset, limit);

        List<RegisterDto> collect = registersWithPaging.stream()
                .map(register -> new RegisterDto(register))
                .collect(toList());

        return collect;

    }




    @GetMapping("/{registerId}/missingAddress")
    MissingAddressDto getMissingAddress(@PathVariable("registerId") Long id,
                                        @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber){

        int limit;
        int offset;
        if(pageNumber == 0){ // 클라이언트가 pageNumber를 입력하지 않았을 때!

            offset = 0; limit = Integer.MAX_VALUE; // 전체 조회

        }
        else if(pageNumber >= 1){

            limit = 5;
            offset = (pageNumber * limit) - limit;

        }
        else
            throw new IllegalStateException("pageNumber는 1부터 입력 가능합니다.");

        List<Register> registerWithId = registerQueryRepository.findRegisterWithId(id,offset,limit);

        List<MissingAddressDto> collect = registerWithId.stream()
                .map(r -> new MissingAddressDto(r.getMissingAddress()))
                .collect(toList());

        return collect.get(0);
    }


}
