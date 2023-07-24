package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class RegisterRepositorySDJTest {

    @Autowired
    RegisterRepositorySDJ registerRepository;


    @Test
    void basicCRUD(){

        //SAVE
        Register register1 = new Register();
        Register register2 = new Register();
        register1.setAnimalName("나리1");
        register2.setAnimalName("나리2");
        Register savedRegister1 = registerRepository.save(register1);
        Register savedRegister2 = registerRepository.save(register2);


        //findById(단건 조회)
        Register byId1 = registerRepository.findById(savedRegister1.getId()).get();
        Register byId2 = registerRepository.findById(savedRegister2.getId()).get();
        assertThat(byId1).isEqualTo(register1);
        assertThat(byId2).isEqualTo(register2);


        //findAll
        List<Register> all = registerRepository.findAll();
        assertThat(all.size()).isEqualTo(10L);


        //count
        long count = registerRepository.count();
        assertThat(count).isEqualTo(10L);


        //delete
        registerRepository.delete(register1);
        registerRepository.delete(register2);

        long deletedCount = registerRepository.count();
        assertThat(deletedCount).isEqualTo(8L);

    }

    @Test
    void countRegisterBy(){

        long count = registerRepository.countRegisterBy();

        assertThat(count).isEqualTo(8L);


    }


    @Test
    void findRegisterById(){

        //givien
         Register register = new Register();
         registerRepository.save(register);

        //when
        Register findRegister = registerRepository.findRegisterById(register.getId());

        //then
        assertThat(findRegister).isEqualTo(register); // 동일성

    }

    @Test
    void findRegistersByAnimalName(){

        //givien
        Register register1 = new Register();
        Register register2 = new Register();
        register1.setAnimalName("사랑이-1");
        register2.setAnimalName("사랑이-1");
        registerRepository.save(register1);
        registerRepository.save(register2);

        //when
        List<Register> 사랑이 = registerRepository.findRegistersByAnimalName("사랑이-1");

        //then
        assertThat(사랑이.size()).isEqualTo(2);

    }
}