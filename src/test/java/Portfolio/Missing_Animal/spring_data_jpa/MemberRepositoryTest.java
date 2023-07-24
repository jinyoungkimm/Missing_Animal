package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

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



}