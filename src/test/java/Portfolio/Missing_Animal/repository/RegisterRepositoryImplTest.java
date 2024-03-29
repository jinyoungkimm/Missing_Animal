package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.animal.Animal;
import Portfolio.Missing_Animal.domainEntity.animal.Dog;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class RegisterRepositoryImplTest {

    @Autowired
    EntityManager em;
    //@Autowired
    //RegisterRepository registerRepository; // 순수 JPA Repository

    @Autowired
    RegisterRepositorySDJ registerRepository; // Spring Data Repository


    @Test
    void save() {

        //givien
        Member member = new Member();
        member.setUsername("김진영");
        member.setUserId("wlsdud6523");
        member.setPassword("eoskan6523");
        em.persist(member);

        Register register = new Register();
        register.setAnimalName("사랑이11111111");
        register.setAnimalAge("6");
        register.setRegisterDate(LocalDateTime.now());
        register.setMember(member);
        em.persist(register);

        //when
        registerRepository.save(register);

        //then

    }

    @Test
    void delete(){

        //givien
        Register finded1 = registerRepository.findById(1L).get();

        //when
        registerRepository.delete(finded1);

        Register finded2 = registerRepository.findById(1L).get();

        //then
        fail("예외가 터졌어야 했다.");

    }

    @Test
    void count(){

        long count = registerRepository.count();

        assertThat(count).isEqualTo(8L);


    }


    @Test
    void findByAnimalId() {

        //givien
        Animal animal = new Dog();
        em.persist(animal);

        Register register = new Register();
        register.setAnimal(animal);
        register.setAnimalName("사랑이");
        register.setAnimalAge("6");
        registerRepository.save(register);

        Long savedId = register.getId();

        //when
        Register findRegister = registerRepository.findById(savedId).get();

        //then
        assertThat(findRegister.getAnimalName()).isEqualTo("사랑이");
    }

    @Test
    void findAll() {

        //givien
        Register register1 = new Register();
        register1.setAnimalName("사랑이1");
        registerRepository.save(register1);

        Register register2 = new Register();
        register2.setAnimalName("사랑이2");
        registerRepository.save(register2);

        Register register3 = new Register();
        register3.setAnimalName("사랑이3");
        registerRepository.save(register3);

        Register register4 = new Register();
        register4.setAnimalName("사랑이4");
        registerRepository.save(register4);

        //when
        List<Register> all = registerRepository.findAll();
        for(int x = 1; x <= all.size();x++){
            assertThat(all.get(x-1).getAnimalName()).isEqualTo("사랑이"+x);
        }

        //then
    }

    @Test
    void findByAnimalName() {

        //givien
        Register register1 = new Register();
        register1.setAnimalName("사랑이1");
        registerRepository.save(register1);

        Register register2 = new Register();
        register2.setAnimalName("사랑이2");
        registerRepository.save(register2);

        Register register3 = new Register();
        register3.setAnimalName("사랑이3");
        registerRepository.save(register3);

        Register register4 = new Register();
        register4.setAnimalName("사랑이4");
        registerRepository.save(register4);

        //when
        List<Register> 사랑이1 = registerRepository.findByAnimalName("사랑이");

        //then
        assertThat(사랑이1.size()).isEqualTo(8);
   
    }
}