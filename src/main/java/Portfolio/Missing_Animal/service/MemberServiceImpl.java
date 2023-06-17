package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.repository.MemberRepository;
import Portfolio.Missing_Animal.repository.MemberRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    //회원가입
    @Override
    @Transactional
    public void join(Member member) {

        //회원ID 중복 검사!
        validateDuplicateMember(member);

        Long saveId = memberRepository.save(member);

    }

    @Override
    public void validateDuplicateMember(Member member) {


        List<Member> findMember = memberRepository.findByUserId(member.getUserId());

        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원ID입니다.");
        }
    }

}
