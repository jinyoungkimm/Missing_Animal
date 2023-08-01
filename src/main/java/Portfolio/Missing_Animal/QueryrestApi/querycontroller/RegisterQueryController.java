package Portfolio.Missing_Animal.QueryrestApi.querycontroller;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterDto;

import Portfolio.Missing_Animal.QueryrestApi.queryrepository.RegisterQueryRepository;
import Portfolio.Missing_Animal.dto.RegisterDtoWithPagination;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForRegister;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterQueryController {

    private final RegisterQueryRepository registerQueryRepository;

    private final StorageServiceForRegister storageService;

    @GetMapping("")
    RegisterDtoWithPagination getRegistersWithPaging(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                             @RequestParam(value = "limit", defaultValue = "2") int limit) {

        int pageNumber = (offset / limit) ; // limit != 0 이라는 보장이 있어야 한다. JPA는 PAGE가 0번부터 시작!
        int size = limit;

        RegisterDtoWithPagination result = registerQueryRepository.findAllRegisters2WithPaging(pageNumber, size);

        return result;

    }

    @GetMapping("/{registerId}")
    RegisterDto getRegistersWithId(@PathVariable("registerId") Long id) {

        try {
            Register registersWithPaging = registerQueryRepository.findRegisterWithOneId(id);

            RegisterDto registerDto = new RegisterDto(registersWithPaging);

            return registerDto;
        }
        catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 Register이 2개이상 조회됨");

        }
        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 Register이 조회되지 않습니다.");

        }
    }

    @GetMapping("/{registerId}/image")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> serveFile(@PathVariable("registerId") Long registerId) throws UnsupportedEncodingException {


        Register register = registerQueryRepository.findRegisterWithOneId(registerId);

        System.out.println(register.getAnimalName());

        String filename = register.getFileName();

        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.status(HttpStatus.OK)
                               .contentType(MediaType.valueOf("image/png"))
                                .body(file);

    }

}
