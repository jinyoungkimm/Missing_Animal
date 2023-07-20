package Portfolio.Missing_Animal.QueryrestApi.querycontroller;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.RegisterDto;

import Portfolio.Missing_Animal.QueryrestApi.queryrepository.RegisterQueryRepository;
import Portfolio.Missing_Animal.service.serviceinterface.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterQueryController {

    private final RegisterQueryRepository registerQueryRepository;

    private final StorageService storageService;


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


        List<Register> registersWithPaging = registerQueryRepository.findAllRegistersWithPaging(offset, limit);

        List<RegisterDto> collect = registersWithPaging.stream()
                .map(register -> new RegisterDto(register))
                .collect(toList());

        return collect;

    }

    @GetMapping("/{registerId}")
    RegisterDto getRegistersWithId(@PathVariable("registerId") Long id) {

        Register registersWithPaging = registerQueryRepository.findRegisterWithOneId(id);

        RegisterDto registerDto = new RegisterDto(registersWithPaging);

        return registerDto;

    }

    @GetMapping("/{registerId}/image")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> serveFile(@PathVariable("registerId") Long registerId) throws UnsupportedEncodingException {

        System.out.println(11111111);

        Register register = registerQueryRepository.findRegisterWithOneId(registerId);

        System.out.println(register.getAnimalName());

        String filename = register.getFileName();

        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.status(HttpStatus.OK)
                               .contentType(MediaType.valueOf("image/png"))
                                .body(file);

    }

}
