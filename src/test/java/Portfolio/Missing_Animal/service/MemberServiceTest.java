package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    @ExceptionHandler(IllegalStateException.class)
    void duplicate() {

        //givien : 기존에 저장되어 있는 데이터
        Member member = new Member();
        member.setUsername("김진영");
        member.setUserId("wlsdud6523");
        //em.persist(member);
        memberService.join(member);

       // em.flush();
       // em.clear();

        //when 새롭게 가입!
        Member newMember = new Member();
        newMember.setUsername("김진영");
        newMember.setUserId("wlsdud65233");
        memberService.join(newMember);

        //then
        fail("예외가 터졌어야 했다.");



    }
}