package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    //@Rollback(false)
    void passwordEncoder(){

        //givien(회원 가입)
        Member oldMember = new Member();
        oldMember.setUserId("wlsdud65231");
        oldMember.setPassword("eoskan6523");
        memberService.join(oldMember);


        //when(로그인)
        Member newMember = new Member();
        newMember.setUserId("wlsdud65231");
        newMember.setPassword("eoskan6523");
        boolean login = memberService.login(newMember);

        //then
        assertThat(login).isTrue();

        // commit()을 만나기 전에는 절대 DB에 SQL문이 반영X.(flush()를 하면, 스냅샵 DB에는 반영이 됨)
    }

    @Test
    //@Rollback(false)
    void duplicate() {

        //givien : 기존에 저장되어 있는 데이터
        Member member = new Member();
        member.setUsername("김진영");
        member.setUserId("wlsdud65231");
        member.setPassword("123");
        //em.persist(member);
        memberService.join(member);

       // em.flush();
       // em.clear();

        //when 새롭게 가입!
        Member newMember = new Member();
        newMember.setUsername("김진영");
        newMember.setUserId("wlsdud65231");
        newMember.setPassword("123");
        memberService.join(newMember);

        //then
        fail("예외가 터졌어야 했다.");

    }

    @Test
    //@Rollback(false)
    void memberInfo(){

        //givien
        String userId = "wlsdud6523";

        //when
        Member member = memberService.memberInfo(userId);

        //then
        assertThat(member.getUsername()).isEqualTo("김진영1");

    }

    @Test
   // @Rollback(false)
    void findRegiserInfo(){

        //givien
        String userId = "wlsdud6523";


        //when
        List<Register> regiserInfo = memberService.findRegiserInfo(userId);


        //then
        assertThat(regiserInfo.get(0).getAnimalName()).isEqualTo("사랑이1");
        assertThat(regiserInfo.get(1).getAnimalName()).isEqualTo("사랑이2");

    }

    @Test
    void findOne( ){

        Member one = memberService.findOne(2L);

        assertThat(one.getUsername()).isEqualTo("김진영2");


    }


}