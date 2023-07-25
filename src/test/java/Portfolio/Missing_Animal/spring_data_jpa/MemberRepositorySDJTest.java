package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberRepositorySDJTest {

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

    @Test
    void findAllWithPaging() throws Exception{

        //givien
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        Member member4 = new Member();
        Member member5 = new Member();
        member1.setUsername("a");
        member1.setUserId("wlsdud65231");

        member2.setUsername("a");
        member2.setUserId("wlsdud652423");

        member3.setUsername("a");
        member3.setUserId("wlsdud652213");

        member4.setUsername("a");
        member4.setUserId("wlsdud652012");

        member5.setUsername("a");
        member5.setUserId("wlsdud652933");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);


        //when
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findMembersByUsername("a", pageRequest);

        List<Member> content = page.getContent(); // 요청한 page에 해당하는 값들
        long totalElements = page.getTotalElements(); // Member 전체의 개수
        int pageNumber = page.getNumber(); // 해당 페이지의 페이지 번호
        boolean isFirstPage = page.isFirst(); // 조회된 페이지가 1번 페이지인가?
        boolean isNextPage = page.hasNext(); // 다음 페이지가 존재를 하는가?
        int totalPages = page.getTotalPages(); // 전체 페이지의 개수

        //then
        assertThat(content.size()).isEqualTo(2L);
        assertThat(totalElements).isEqualTo(5L);
        assertThat(totalPages).isEqualTo(3L);
        assertThat(pageNumber).isEqualTo(0);
        assertThat(isFirstPage).isTrue();
        assertThat(isNextPage).isTrue();




    }


    @Test
    void findAll(){

        //when
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<Member> page = memberRepository.findAll(pageRequest);


        //then
        List<Member> content = page.getContent();
        for (Member member : content) {
            System.out.println(member);
        }

        int totalPages = page.getTotalPages();
        assertThat(totalPages).isEqualTo(2L);

        long totalElements = page.getTotalElements();
        assertThat(totalElements).isEqualTo(4L);

        boolean isNextPage = page.hasNext();
        assertThat(isNextPage).isTrue();


    }


}