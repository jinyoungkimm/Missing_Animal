package Portfolio.Missing_Animal.domainEntity;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ReportTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    void a(){

        //givien
        Register register = new Register();
        register.setAnimalName("칸다");
        em.persist(register);


        Report report1 = new Report();
        report1.setRegister(register);
        report1.setFindedTime(LocalDateTime.now());
        em.persist(report1);

        Report report2 = new Report();
        report2.setRegister(register);
        report2.setFindedTime(LocalDateTime.now());
        em.persist(report2);


        //when


        //then


    }


    @Test
    void 연관관계_메서드(){

        Report report1 = new Report();
        Report report2 = new Report();
        em.persist(report1);
        em.persist(report2);

        Register register1 = new Register();
        register1.setAnimalName("사랑이1");

        Register register2 = new Register();
        register2.setAnimalName("사랑이2");

        Member member1 = new Member();
        member1.setUsername("김진영1");
        member1.setPassword("eoskan6523");
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김진영2");
        member2.setPassword("eoskan6524");
        em.persist(member2);

        register1.setMember(member1); // 연관 관계 메서드 사용(member1에도 양방향으로 register1을 가리키고 있음)

        report1.setMember(member1);      // 연관 관계 메서드 사용(member1에도 양방향으로 report1을 가리키고 있음)
        report1.setRegister(register1); // 연관 관계 메서드 사용(register1에도 양방향으로 report1을 가리키고 있음)

        register1.addReport(report1);
        register1.addReport(report2);
        em.persist(register1);

        register2.setMember(member2); // 연관 관계 메서드 사용(member2에도 양방향으로 register2을 가리키고 있음)

        report2.setMember(member2); // 이하 동문
        report2.setRegister(register2); // 이하 동문

        register2.addReport(report2);
        em.persist(register2);

        // ==============================================================================================
        // 즉, 기존에는 연관 관계 주인에서만 다른 객체를 참조가 가능하였지만,
        // 관련된 모든 엔티티끼리 서로 양방향으로 연결이 되어 있으므로, 역참조가 가능하다.

        Assertions.assertThat(report1.getRegister().getMember().getRegisters().get(0).getAnimalName()).isEqualTo("사랑이1");
        Assertions.assertThat(report2.getRegister().getMember().getRegisters().get(0).getAnimalName()).isEqualTo("사랑이2");

    }

}