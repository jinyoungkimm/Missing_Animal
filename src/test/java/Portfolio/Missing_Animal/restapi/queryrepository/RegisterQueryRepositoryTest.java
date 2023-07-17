package Portfolio.Missing_Animal.restapi.queryrepository;

import Portfolio.Missing_Animal.domain.Register;
import org.aspectj.lang.annotation.RequiredTypes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterQueryRepositoryTest {

    @Autowired
    RegisterQueryRepository repository;


    @Test
    void findAllRegisters() {

        //when
        List<Register> allRegisters = repository.findAllRegisters();

        //then
         for(int x = 1; x <= allRegisters.size();x++){

          assertThat(allRegisters.get(x-1).getAnimalName()).isEqualTo("사랑이"+x);

      }
    }

    @Test
    void findRegisterWithId() {

        //givien
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;
        int offset = 0;
        int limit = Integer.MAX_VALUE;
        //when
        Register registerWithId1 = repository.findRegisterWithId(id1).get(0);
        Register registerWithId2 = repository.findRegisterWithId(id2).get(0);
        Register registerWithId3 = repository.findRegisterWithId(id3).get(0);

        //then
        assertThat(registerWithId1.getAnimalName()).isEqualTo("사랑이1");
        assertThat(registerWithId2.getAnimalName()).isEqualTo("사랑이2");
        assertThat(registerWithId3.getAnimalName()).isEqualTo("사랑이3");


    }
}