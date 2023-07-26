package Portfolio.Missing_Animal.QueryrestApi.queryrepository;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.dto.MissingAddressDto;
import Portfolio.Missing_Animal.dto.MissingAddressDtoWithPagination;
import Portfolio.Missing_Animal.dto.RegisterDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MissingAddressQueryRepositoryTest {

    @Autowired
    MissingAddressQueryRepository repository;


    @Test
    void findAllMissingAddress() {

        List<MissingAddress> allMissingAddress = repository.findAllMissingAddress();

        assertThat(allMissingAddress.size()).isEqualTo(4);

    }

    @Test
    void findAllMissingAddress2(){

        List<MissingAddressDto> allMissingAddress2 = repository.findAllMissingAddress2();

        Assertions.assertThat(allMissingAddress2.size()).isEqualTo(4);

        for (MissingAddressDto missingAddressDto : allMissingAddress2) {

            List<RegisterDto> registers = missingAddressDto.getRegisters();

            for (RegisterDto register : registers) {

                System.out.println(register.getAnimalName());

            }

        }
    }

    @Test
    void findAllMissingAddress2WithPaging(){


        MissingAddressDtoWithPagination allMissingAddress2WithPaging = repository.findAllMissingAddress2WithPaging(0, 2);

        System.out.println(allMissingAddress2WithPaging.getPagination());
        System.out.println(allMissingAddress2WithPaging.getMissingAddressDtos());

    }


    @Test
    void findById() {

        //givien
        Long id = 3L;

        //when
        MissingAddress findMissingAddress = repository.findByOneId(id);


        //then
        assertThat(findMissingAddress.getCityName()).isEqualTo("전주시1");

    }

    @Test
    void findById2() {

        //givien
        Long id = 3L;

        //when
        MissingAddressDto findMissingAddress = repository.findByOneId2(id);

        List<RegisterDto> registers = findMissingAddress.getRegisters();


        //then
        assertThat(findMissingAddress.getCityName()).isEqualTo("전주시1");

        assertThat(registers.get(0).getAnimalName()).isEqualTo("사랑이5");
        assertThat(registers.get(1).getAnimalName()).isEqualTo("사랑이6");

    }

    // 이 아래 테스트는 Qurydsl로 튜닝을 한 뒤에 하겠다.

    @Test
    void findRegisterWithPrefecture() {

        //givien

        //when

        //then



    }

    @Test
    void findRegisterWithCityOrStreet() {


        //givien

        //when

        //then
    }
}