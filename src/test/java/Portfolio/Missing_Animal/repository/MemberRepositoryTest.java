package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    void save(){

        //given
        Member member = new Member();
        member.setUsername("kim");

        //when
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();


        //then
        Assertions.assertThat(id).isEqualTo(memberRepository.findById(id).getId());


    }

    @Test
    @Rollback(false)
    void findById(){

        //given
        Member member = new Member();
        member.setUsername("kim1");
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(id);

        //then
        Assertions.assertThat(findMember.getUsername()).isEqualTo("kim1");

    }


    @Test
    @Rollback(false)
    void findAll(){

        //given
        Member member1 = new Member();
        member1.setUsername("kim1");
        Member member2 = new Member();
        member2.setUsername("kim2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //when
        List<Member> findMembers = memberRepository.findAll();
        Assertions.assertThat(findMembers.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(findMembers.get(1).getId()).isEqualTo(2);


    }

    @Test
    @Rollback(false)
    void findByName(){

        //given
        Member member = new Member();
        member.setUsername("kim1");
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();

        //when

        List<Member> findMembers = memberRepository.findByName("kim1");



        //then
        Assertions.assertThat(findMembers.get(0).getUsername()).isEqualTo("kim1");


    }


}