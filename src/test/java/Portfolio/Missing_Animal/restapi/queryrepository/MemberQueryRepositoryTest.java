package Portfolio.Missing_Animal.restapi.queryrepository;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class MemberQueryRepositoryTest {

    @Autowired
    MemberQueryRepository memberQueryRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void findAllMembers() {

        List<Member> allMembers = memberQueryRepository.findAllMembers();

       allMembers.get(0).getRegisters().get(0).getAnimalName(); // fetch join을 사용하였기에 (1+N) 문제 해결
       allMembers.get(0).getRegisters().get(1).getAnimalName(); // fetch join을 사용하였기에 (1+N) 문제 해결

    }

    @Test
    @Transactional
    void findMembers3(){

        List<MemberDto> allMembers3 = memberQueryRepository.findAllMembers3();

    }

    @Test
    @Transactional
    void findMembers4(){

        List<MemberDto> allMembers3 = memberQueryRepository.findAllMembers4();

        assertThat(allMembers3.size()).isEqualTo(4);

        for (MemberDto memberDto : allMembers3) {

            System.out.println(memberDto.getRegisters().get(0).getAnimalName());
            System.out.println(memberDto.getRegisters().get(1).getAnimalName());

        }

    }


    @Test
    void findMemberWithUserId() {

        //givien
        String userId = "wlsdud6523";

        //when
        List<Member> memberWithUserId = memberQueryRepository.findMemberWithUserId(userId);

        //then
        // fetch join을 사용하였기에 (1+N) 문제 해결
        assertThat(memberWithUserId.get(0).getRegisters().get(0).getAnimalName()).isEqualTo("사랑이1");
        assertThat(memberWithUserId.get(0).getRegisters().get(1).getAnimalName()).isEqualTo("사랑이2");


    }
}