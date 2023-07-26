package Portfolio.Missing_Animal.domainEntity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class RegisterTest {

    @Autowired
    EntityManager em;

    @Test
    void 연관관계_메서드(){


      /*  Member member1 = new Member();
        member1.setUsername("김진영1");
        member1.setPassword("eoskan6523");
        em.persist(member1);

       *//* Member member2 = new Member();
        member2.setUsername("김진영2");
        member2.setPassword("eoskan6524");
        em.persist(member2);*//*


        Register register1 = new Register();
        register1.setMember(member1); // 연관 관계 메서드 사용(member1에도 register1가 [자동]으로 세팅돼 있다)
        register1.setAnimalName("사랑이1");
        em.persist(register1);

        Register register2 = new Register();
        register2.setMember(member1); // 연관 관계 메서드 사용(member1에도 register2가 [자동]으로 세팅돼 있다)
        register2.setAnimalName("사랑이2");
        em.persist(register2);


        Report report = new Report();

        register1.addReport(report);
        register2.addReport(report);

        em.persist(report);*/

        Register register1 = new Register();
        register1.setAnimalName("사랑이1");

        Register register2 = new Register();
        register2.setAnimalName("사랑이2");

        Member member1 = new Member();
        member1.setUsername("김진영1");
        member1.setPassword("eoskan6523");
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김진영1");
        member2.setPassword("eoskan6523");
        em.persist(member2);


        register1.setMember(member1); // 연관 관계 메서드 사용(member1에도 양방향으로 register1을 가리키고 있음)
        register2.setMember(member2); // 연관 관계 메서드 사용(member2에도 양방향으로 register2을 가리키고 있음)

        // ==============================================================================================
        // 즉, 기존에는 연관 관계 주인에서만 다른 객체를 참조가 가능하였지만,
        // 관련된 모든 엔티티끼리 서로 양방향으로 연결이 되어 있으므로, 역참조가 가능하다.

        // register <-> member : 양방향으로 참조 가능!
        Member member = register1.getMember();
        assertThat(member.getUsername()).isEqualTo(member1.getUsername());

        List<Register> registers = member.getRegisters();
        for (Register register : registers) {

            assertThat(register.getMember().getUsername()).isEqualTo(member.getUsername());

        }

    }


}