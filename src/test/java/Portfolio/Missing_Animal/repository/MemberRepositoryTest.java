package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.spring_data_jpa.MemberRepositorySDJ;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    //@Autowired
    //MemberRepositoryImpl memberRepository; // 순수 JPA Repository

    @Autowired
    MemberRepositorySDJ memberRepository; // Spring Data JPA

    @Autowired
    EntityManager em;

    @Test
    void save(){

        // 순수 JPA Repository
        /*//given
        Member member = new Member();
        member.setUsername("kim");

        //when
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();


        //then
        assertThat(id).isEqualTo(memberRepository.findById(id).getId());*/

        // Spring Data JPA
        //given
        Member member = new Member();
        member.setUsername("kim");

        //when
        Member save = memberRepository.save(member);
        Long id = save.getId();

        em.flush();
        em.clear();


        //then
        assertThat(id).isEqualTo(memberRepository.findById(id).get().getId());



    }

    @Test
    void delete(){
        // 순수 JPA Repository
       /* //givien
        Member findMember1 = memberRepository.findById(1L);

        //when
        memberRepository.delete(findMember1);

        //then
        Member findMember2 = memberRepository.findById(1L);

        fail("예외가 터졌어야 했다.");*/

        // Spring Data JPA
        //givien
        Member findMember1 = memberRepository.findById(1L).get();

        //when
        memberRepository.delete(findMember1);

        //then
        Member findMember2 = memberRepository.findById(1L).get();

        fail("예외가 터졌어야 했다.");
    }

    @Test
    void count(){
        // 순수 JPA Repository
        /*long count = memberRepository.count();


        assertThat(count).isEqualTo(4l);*/


        // Spring Data JPA
        long count = memberRepository.count();


        assertThat(count).isEqualTo(4l);

    }

    @Test
    void findById(){
        // 순수 JPA Repository
        /*//given
        Member member = new Member();
        member.setUsername("kim1");
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(id);

        //then
        assertThat(findMember.getUsername()).isEqualTo("kim1");*/

        // Spring Data JPA
        //given
        Member member = new Member();
        member.setUsername("kim1");
        Long id = memberRepository.save(member).getId();

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(id).get();

        //then
        assertThat(findMember.getUsername()).isEqualTo("kim1");

    }


    @Test
    void findAll(){

        // 순수 JPA Repository
       /* //when
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
        assertThat(findMembers.get(1).getId()).isEqualTo(2);*/


        // Spring Data JPA
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
    void findByName(){
        // 순수 JPA Repository
       /* //given
        Member member = new Member();
        member.setUsername("kim1");
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();

        //when

        List<Member> findMembers = memberRepository.findByUserName("kim1");



        //then
        assertThat(findMembers.get(0).getUsername()).isEqualTo("kim1");*/

        // Spring Data JPA
        //given
        Member member = new Member();
        member.setUsername("kim1");
        Long id = memberRepository.save(member).getId();

        em.flush();
        em.clear();

        //when

        List<Member> findMembers = memberRepository.findByUsername("kim1");



        //then
        assertThat(findMembers.get(0).getUsername()).isEqualTo("kim1");

    }

}