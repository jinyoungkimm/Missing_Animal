package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryImpl memberRepository;

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
        assertThat(id).isEqualTo(memberRepository.findById(id).getId());


    }

    @Test
    void delete(){

        //givien
        Member findMember1 = memberRepository.findById(1L);

        //when
        memberRepository.delete(findMember1);

        //then
        Member findMember2 = memberRepository.findById(1L);

        fail("예외가 터졌어야 했다.");
    }

    @Test
    void count(){

        long count = memberRepository.count();


        assertThat(count).isEqualTo(4l);
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
        assertThat(findMember.getUsername()).isEqualTo("kim1");

    }


    @Test
    @Rollback(false)
    void findAll(){


        //when
        List<Member> findMembers = memberRepository.findAll();
        for (Member findMember : findMembers) {

            System.out.println("=============");
            List<Register> registers = findMember.getRegisters();
            for (Register register : registers) {
                System.out.println(register.getId());
            }

            System.out.println("=============");
            List<Report> reports = findMember.getReports();
            for (Report report : reports) {
                System.out.println(report.getId());
            }

        }
        assertThat(findMembers.get(0).getId()).isEqualTo(1);
        assertThat(findMembers.get(1).getId()).isEqualTo(2);


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
        assertThat(findMembers.get(0).getUsername()).isEqualTo("kim1");


    }

}