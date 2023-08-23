package Portfolio.Missing_Animal.QueryrestApi.querycontroller;

import Portfolio.Missing_Animal.QueryrestApi.queryrepository.MissingAddressQueryRepository;
import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.dto.MissingAddressDto;
import Portfolio.Missing_Animal.dto.MissingAddressDtoWithPagination;
import Portfolio.Missing_Animal.dto.MissingAddressSearchCond;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missingaddress")// @ResponseBody를 자동으로 붙여줌
@RequiredArgsConstructor
@LogTrace
public class MissingAddressQueryController {


    private final MissingAddressQueryRepository missingAddressQueryRepository;


    @GetMapping("") // [페이징] 가능!
    MissingAddressDtoWithPagination getAllMissingAddress(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                                          @RequestParam(value="limit",defaultValue = "2") int limit){


        int pageNumber = (offset / limit) ; // limit != 0 이라는 보장이 있어야 한다. JPA는 PAGE가 0번부터 시작!
        int size = limit;

        MissingAddressDtoWithPagination result = missingAddressQueryRepository.findAllMissingAddress2WithPaging(pageNumber, size);

        return result;

    }

    @GetMapping("/{id}")
    MissingAddressDto getMissingAddressWithID1(@PathVariable("id") Long id){

        try {
            MissingAddressDto findMissingAddress = missingAddressQueryRepository.findByOneId2(id);

            return findMissingAddress;
        }
          catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 missingAddress가 2개이상 조회됨");

        }
        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 missingAddress가 조회되지 않습니다.");

        }
    }

    // 여기서부터 아래는 Querydsl로 구현을 나중에 할 것이다.
    /**
     * 2. api/missingaddress/{prefecture }
     * 3. api/missingaddress/{prefecture }/{cityName}
     * 4. api/missingaddress{prefecture }/{cityName}/{gu}
     * 5. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}
     * 6. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}/{streetNumber}
     * 6. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}/{streetNumber}/{zipcode}
     * @param
     * @return
     */

    @PostMapping("")
    List<MissingAddressDto> getRegisterInPrefecture(@RequestBody MissingAddressSearchCond missingAddressSearchCond){

        List<MissingAddressDto> missingAddress = missingAddressQueryRepository.findRegisterByMissingAddress(missingAddressSearchCond);

        System.out.println("size"+missingAddress.size());

        return missingAddress;

    }

}
