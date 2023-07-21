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