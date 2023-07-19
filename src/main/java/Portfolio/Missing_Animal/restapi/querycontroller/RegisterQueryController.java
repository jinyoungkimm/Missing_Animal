package Portfolio.Missing_Animal.restapi.querycontroller;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.MissingAddressDto;
import Portfolio.Missing_Animal.dto.RegisterDto;

import Portfolio.Missing_Animal.restapi.queryrepository.MissingAddressQueryRepository;
import Portfolio.Missing_Animal.restapi.queryrepository.RegisterQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterQueryController {

    private final RegisterQueryRepository registerQueryRepository;

    private final MissingAddressQueryRepository missingAddressQueryRepository;


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
    List<RegisterDto> getRegistersWithId(@PathVariable("registerId") Long id) {



        List<Register> registersWithPaging = registerQueryRepository.findRegisterWithOneId(id);

        List<RegisterDto> collect = registersWithPaging.stream()

                .map(register -> new RegisterDto(register))

                .collect(toList());

        return collect;

    }

   /* @GetMapping("/{registerId}/image")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("registerId") Long registerId) throws UnsupportedEncodingException {


        Resource file = storageService.loadAsResource(filename);


        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(file.getFilename(),"UTF-8") + "\"")
                .body(file);

    }*/



}
