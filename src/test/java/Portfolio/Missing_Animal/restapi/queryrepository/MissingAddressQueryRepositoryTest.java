package Portfolio.Missing_Animal.restapi.queryrepository;

import Portfolio.Missing_Animal.domain.MissingAddress;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MissingAddressQueryRepositoryTest {

    @Autowired
    MissingAddressQueryRepository repository;


    @Test
    void findAllMissingAddress() {

        List<MissingAddress> allMissingAddress = repository.findAllMissingAddress();

        assertThat(allMissingAddress.size()).isEqualTo(4);

    }

    @Test
    void findById() {

        //givien
        Long id = 3L;

        //when
        MissingAddress findMissingAddress = repository.findById(id).get(0);


        //then
        assertThat(findMissingAddress.getCityName()).isEqualTo("전주시1");

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