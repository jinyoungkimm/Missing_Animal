package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.Register;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        Register findRegister = registerRepository.findById(register.getId()).get();

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
        List<Register> 사랑이 = registerRepository.findByAnimalName("사랑이-1");

        //then
        assertThat(사랑이.size()).isEqualTo(2);

    }

    @Test
    void findRegistersByAnimalNameWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<Register> page = registerRepository.findByAnimalName("사랑이1",  pageRequest);

        List<Register> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for (Register register : content) {
            System.out.println(register);
        }
        assertThat(totalElements).isEqualTo(1L);

        assertThat(totalPages).isEqualTo(1L);

        assertThat(isNextPage).isFalse();

    }

    @Test
    void findAllWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<Register> page = registerRepository.findAll(pageRequest);

        List<Register> content = page.getContent();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        boolean isNextPage = page.hasNext();

        //then
        for (Register register : content) {
            System.out.println(register);
        }
        assertThat(totalElements).isEqualTo(8L);
        assertThat(totalPages).isEqualTo(4L);
        assertThat(isNextPage).isTrue();

    }

}