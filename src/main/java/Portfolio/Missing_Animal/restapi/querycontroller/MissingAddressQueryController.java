package Portfolio.Missing_Animal.restapi.querycontroller;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.MissingAddressDto;
import Portfolio.Missing_Animal.dto.RegisterDto;
import Portfolio.Missing_Animal.restapi.queryrepository.MissingAddressQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/missingaddress")// @ResponseBody를 자동으로 붙여줌
@RequiredArgsConstructor
public class MissingAddressQueryController {


    private final MissingAddressQueryRepository missingAddressQueryRepository;

   /* @GetMapping("") //[페이징] 불가!
    List<MissingAddressDto> getAllMissingAddress1(){

        List<MissingAddress> allMissingAddress = missingAddressQueryRepository.findAllMissingAddress();

        List<MissingAddressDto> collect = allMissingAddress.stream()
                .map(mr -> new MissingAddressDto(mr))
                .collect(toList());

        return collect;

    }*/

    @GetMapping("") // [페이징] 가능!
    List<MissingAddressDto> getAllMissingAddress2(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                                  @RequestParam(value="limit",defaultValue = "1") int limit){

        List<MissingAddress> allMissingAddress = missingAddressQueryRepository.findAllMissingAddress2(offset, limit);

        List<MissingAddressDto> collect = allMissingAddress.stream()
                .map(mr -> new MissingAddressDto(mr))
                .collect(toList());

        return collect;

    }

    @GetMapping("/{id}/missingaddress")
    MissingAddressDto getMissingAddressWithID1(@PathVariable("id") Long id){ //[페이징 불가능]

        List<MissingAddress> findMissingAddress = missingAddressQueryRepository.findById(id);

        List<MissingAddressDto> collect = findMissingAddress.stream()
                .map(mr -> new MissingAddressDto(mr))
                .collect(toList());

        return collect.get(0);

    }




    // 여기서부터 아래는 Querydsl로 구현을 나중에 할 것이다.

    /**
     * 2. api/missingaddress/{prefecture }
     * 3. api/missingaddress/{prefecture }/{cityName}
     * 4. api/missingaddress{prefecture }/{cityName}/{gu}
     * 5. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}
     * 6. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}/{streetNumber}
     * @param prefecture
     * @return
     */

    @GetMapping("/{prefecture}")
    List<MissingAddressDto> getRegisterInPrefecture(@PathVariable("prefecture") String prefecture){

        List<MissingAddress> missingAddressesWithPrefecture = missingAddressQueryRepository.findRegisterWithPrefecture(prefecture);

        List<MissingAddressDto> collect = missingAddressesWithPrefecture.stream().
                map(mr -> new MissingAddressDto(mr)).
                collect(toList());

        return collect;

    }


  /* @GetMapping("/{cityName}/{streetName}")
    List<RegisterDto> getRegisterInCity(@PathVariable("cityName") String cityName,@PathVariable("streetName") String streetName){


    }*/












}
