package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepositorySDJ memberRepository;

    @Test
    @Rollback(value = false)
    void testMember(){


        Member member = new Member();
        member.setUsername("진영이");
        Member save = memberRepository.save(member);

        Long saveId = save.getId();
        Member findById = memberRepository.findById(saveId).get();

        Assertions.assertThat(save.getId()).isEqualTo(findById.getId());
        Assertions.assertThat(save).isEqualTo(findById);


    }



    @Test
    void basicCRUD(){

        //SAVE
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setUsername("나리1");
        member2.setUsername("나리2");
        Member savedmember1 = memberRepository.save(member1);
        Member savedmember2 = memberRepository.save(member2);


        //findById(단건 조회)
        Member byId1 = memberRepository.findById(savedmember1.getId()).get();
        Member byId2 = memberRepository.findById(savedmember2.getId()).get();
        assertThat(byId1).isEqualTo(member1);
        assertThat(byId2).isEqualTo(member2);


        //findAll
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(6L);


        //count
        long count = memberRepository.count();
        assertThat(count).isEqualTo(6L);


        //delete
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(4L);

    }



    @Test
    void countMemberById(){

        long memberCount = memberRepository.countMemberBy();

        assertThat(memberCount).isEqualTo(4L);

    }

    @Test
    void findMemberById(){

        //givien
        Member member = new Member();
        member.setUsername("진영이");
        Member save = memberRepository.save(member);

        //when
        Member memberById = memberRepository.findMemberById(save.getId());

        //then
        assertThat(memberById).isEqualTo(member); // 동일성


    }

    @Test
    void findMemberByUserId(){

        //givien
        Member member = new Member();
        member.setUserId("wlsdud6520");
        Member save = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findMemberByUserId("wlsdud6520");


        //then
        assertThat(findMember).isEqualTo(save);



    }

    @Test
    void findMembersByUserName(){

        //givien
        Member member = new Member();
        member.setUsername("진영이");
        Member save = memberRepository.save(member);

        //when
        List<Member> 진영이 = memberRepository.findMembersByUsername("진영이");

        //then
        assertThat(진영이.size()).isEqualTo(1L);


    }


}