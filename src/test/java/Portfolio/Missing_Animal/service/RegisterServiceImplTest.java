package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RegisterServiceImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    RegisterService registerService;

    @Autowired
    RegisterRepository registerRepository;


    @Test
    @Transactional
    void registerMissing() {

        //givien
        Member member = new Member();
        member.setUsername("김진영");
        member.setUserId("wlsdud6523");
        member.setPassword("eoskan6523");
        em.persist(member);

        Register register = new Register();
        register.setAnimalName("사랑이");
        register.setAnimal_age("6");
        register.setRegisterDate(LocalDateTime.now());
        register.setMember(member);
        em.persist(register);

        Long savedId = register.getId();

        //when
        registerService.registerMissing(register);

        List<Register> 사랑이 = registerRepository.findByAnimalName("사랑이");

        //then
        assertThat(사랑이.size()).isEqualTo(1);
        assertThat(사랑이.get(0).getId()).isEqualTo(savedId);

    }

    @Test
    @Rollback(false)
    @Transactional
    void listingRegister() {


        //givien
        Member member1 = new Member();
        member1.setUsername("김진영1");
        member1.setUserId("wlsdud65231");
        member1.setPassword("eoskan65231");
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김진영2");
        member2.setUserId("wlsdud65232");
        member2.setPassword("eoskan65232");
        em.persist(member2);

        Member member3 = new Member();
        member3.setUsername("김진영3");
        member3.setUserId("wlsdud65233");
        member3.setPassword("eoskan65233");
        em.persist(member3);

        Member member4 = new Member();
        member4.setUsername("김진영4");
        member4.setUserId("wlsdud65234");
        member4.setPassword("eoskan65234");
        em.persist(member4);


        Register register1 = new Register();
        register1.setAnimalName("사랑이1");
        register1.setMember(member1);
        registerService.registerMissing(register1);


        Register register2 = new Register();
        register2.setAnimalName("사랑이2");
        register2.setMember(member2);
        registerService.registerMissing(register2);


        Register register3 = new Register();
        register3.setAnimalName("사랑이3");
        register3.setMember(member3);
        registerService.registerMissing(register3);


        Register register4 = new Register();
        register4.setAnimalName("사랑이4");
        register4.setMember(member4);
        registerService.registerMissing(register4);

        //when

        List<Register> registers = registerService.listingRegister();

        assertThat(registers.size()).isEqualTo(4);

        for (int x = 1; x <= registers.size();x++) {
            assertThat(registers.get(x-1).getAnimalName()).isEqualTo("사랑이"+x);
        }


    }
}