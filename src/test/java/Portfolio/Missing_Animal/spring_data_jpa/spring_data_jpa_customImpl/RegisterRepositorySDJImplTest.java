package Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_customImpl;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        //registerSearchCond.setAnimalAge();
        //registerSearchCond.setAnimalSex();
        //registerSearchCond.setAnimalVariety();

        registerSearchCond.setPrefecture("충청");
        registerSearchCond.setCityName("천안");
        //registerSearchCond.setGu();
        //registerSearchCond.setDong();
        //registerSearchCond.setStreetName();
        //registerSearchCond.setStreetNumber();

        //when
        List<Tuple> registers = registerRepositorySDJ.searchRegisters(registerSearchCond);


        //then
        for (Tuple register : registers) {
            System.out.println("register = " + register);
        }

    }




}