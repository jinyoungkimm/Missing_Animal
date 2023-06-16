package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원가입
    @Transactional
    public void join(Member member){

        Long saveId = memberRepository.save(member);


    }


}
