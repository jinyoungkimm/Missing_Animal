package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public void join(Member member) {

        //회원ID 중복 검사!
        validateDuplicateMember(member);

        Long saveId = memberRepository.save(member);

    }


    private void validateDuplicateMember(Member member) {


        List<Member> findMember = memberRepository.findByUserId(member.getUserId());

        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원ID입니다.");
        }
    }

}
