package Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_customImpl;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import com.querydsl.core.Tuple;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RegisterRepositorySDJImplTest {

    @Autowired
    RegisterRepositorySDJ registerRepositorySDJ;


    @Test
    void findByMissingAddressCond() {

        //givien
        // 모든 조건을 다 적지 않아도, BooleanExpression에 의해서 null 값들은 WHERE 문이 자동으로 무시한다.
        RegisterSearchCond registerSearchCond = new RegisterSearchCond();
        registerSearchCond.setAnimalName("사랑이");
        //registerSearchCond.setAnimalAge(); // 생략
        //registerSearchCond.setAnimalSex(); // 생략
        //registerSearchCond.setAnimalVariety(); // 생략

        registerSearchCond.setPrefecture("충청");
        registerSearchCond.setCityName("천안");
        //registerSearchCond.setGu(); // 생략
        //registerSearchCond.setDong(); // 생략
        //registerSearchCond.setStreetName(); // 생략
        //registerSearchCond.setStreetNumber(); // 생략

        //when
        List<Register> registers = registerRepositorySDJ.searchRegisters(registerSearchCond);


        //then
        for (Register register : registers) {
            System.out.println("register = " + register);
        }

    }


    @Test
    void searchRegistersWithPagingSimple(){

        //givien
        // 모든 조건을 다 적지 않아도, BooleanExpression에 의해서 null 값들은 WHERE 문이 자동으로 무시한다.
        RegisterSearchCond registerSearchCond = new RegisterSearchCond();
        registerSearchCond.setAnimalName("사랑이");
        //registerSearchCond.setAnimalAge(); // 생략
        //registerSearchCond.setAnimalSex(); // 생략
        //registerSearchCond.setAnimalVariety(); // 생략

        registerSearchCond.setPrefecture("충청");
        registerSearchCond.setCityName("천안");
        //registerSearchCond.setGu(); // 생략
        //registerSearchCond.setDong(); // 생략
        //registerSearchCond.setStreetName(); // 생략
        //registerSearchCond.setStreetNumber(); // 생략

        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<Register> page = registerRepositorySDJ.searchRegistersWithPagingSimple(registerSearchCond, pageRequest);

        List<Register> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for (Register tuple : content) {
            System.out.println("tuple = " + tuple);
        }

         assertThat(totalPages).isEqualTo(4L);
         assertThat(totalElements).isEqualTo(4L);
         assertThat(isNextPage).isTrue();

    }


    @Test
    void searchRegistersWithPagingComplexV1(){

        //givien
        // 모든 조건을 다 적지 않아도, BooleanExpression에 의해서 null 값들은 WHERE 문이 자동으로 무시한다.
        RegisterSearchCond registerSearchCond = new RegisterSearchCond();
        registerSearchCond.setAnimalName("사랑이");
        //registerSearchCond.setAnimalAge(); // 생략
        //registerSearchCond.setAnimalSex(); // 생략
        //registerSearchCond.setAnimalVariety(); // 생략

        registerSearchCond.setPrefecture("충청");
        registerSearchCond.setCityName("천안");
        //registerSearchCond.setGu(); // 생략
        //registerSearchCond.setDong(); // 생략
        //registerSearchCond.setStreetName(); // 생략
        //registerSearchCond.setStreetNumber(); // 생략

        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<Register> page = registerRepositorySDJ.searchRegistersWithPagingComplexV1(registerSearchCond, pageRequest);

        List<Register> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for (Register tuple : content) {
            System.out.println("tuple = " + tuple);
        }

        assertThat(totalPages).isEqualTo(4L);
        assertThat(totalElements).isEqualTo(4L);
        assertThat(isNextPage).isTrue();

    }

    @Test
    void searchRegistersWithPagingComplexV2(){

        //givien
        // 모든 조건을 다 적지 않아도, BooleanExpression에 의해서 null 값들은 WHERE 문이 자동으로 무시한다.
        RegisterSearchCond registerSearchCond = new RegisterSearchCond();
        registerSearchCond.setAnimalName("사랑이");
        //registerSearchCond.setAnimalAge(); // 생략
        //registerSearchCond.setAnimalSex(); // 생략
        //registerSearchCond.setAnimalVariety(); // 생략

        registerSearchCond.setPrefecture("충청");
        registerSearchCond.setCityName("천안");
        //registerSearchCond.setGu(); // 생략
        //registerSearchCond.setDong(); // 생략
        //registerSearchCond.setStreetName(); // 생략
        //registerSearchCond.setStreetNumber(); // 생략

        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<Register> page = registerRepositorySDJ.searchRegistersWithPagingComplexV1(registerSearchCond, pageRequest);

        List<Register> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for (Register tuple : content) {
            System.out.println("tuple = " + tuple);
        }

        assertThat(totalPages).isEqualTo(4L);
        assertThat(totalElements).isEqualTo(4L);
        assertThat(isNextPage).isTrue();

    }


}