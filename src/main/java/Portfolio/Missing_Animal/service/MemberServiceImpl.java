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
    private final PasswordEncoder bCryptPasswordEncoder; // passWordEncoder는 [인터페이스]이며, BCryptPasswordEncoder는 그 [구현체]이다.

    //회원가입
    @Override
    @Transactional
    public void join(Member member) {

        //회원ID 중복 검사!
        validateDuplicateMember(member);
        System.out.println(member);
        /**
         * Plain 비밀번호를 암호화 시켜서, 저장을 시킨다.
         */
        Member newMember = member.hashPassword(bCryptPasswordEncoder); // member 객체 안의 평문 비밀번호가 암호화된 비밀번호로 교체된다.
        Long saveId = memberRepository.save(newMember);

    }

    @Override
    public void validateDuplicateMember(Member member) {


        List<Member> findMember = memberRepository.findByUserId(member.getUserId());

        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원ID입니다.");
        }
    }

    //로그인 기능(DB에 저장된 id값을 반환)
    @Override
    public boolean login(Member member) {

        /**
         * 먼저, 로그인 시 입력한 ID가, 회원가입이 된 ID인지를 검사해야 한다.
         */

        //먼저 해당 ID가 이미 가입되어 있는지를 검사한다.
        List<Member> findUser = memberRepository.findByUserId(member.getUserId());
       if(findUser.isEmpty() == true)
           throw new IllegalStateException("로그인 시 입력한 ID의 회원이 존재하지 않습니다.");

        /**
         *  이 시점에서, 해당 ID가 가입된 ID임은 증명이 됐다.
         *  다음으로는 db에 저장된 비밀번호와 로그인 시 입력한 비밀번호가 맞는지를 검사한다.
         */

        // [암호화된 비밀번호]가 들어 있는 Member 객체이다.
        Member findMember = findUser.get(0);

        // 암호화된 비밀번호(findMember::password)와 평문 비밀번호(매개변수 member::password)를 비교한다.
        boolean isSame = findMember.checkPassword(member.getPassword(), bCryptPasswordEncoder);

        // 비교해서 맞으면, controller에 true를 넘기고, 아니면 throw를 날려 준다.
        if(isSame == true)
            return true;
        else
            throw new IllegalStateException("비밀번호가 틀렸습니다.");

    }

    @Override
    public boolean isMemberExist(Member member) {

        List<Member> findMember = memberRepository.findByUserId(member.getUserId());

        if(findMember.isEmpty() == true)
            return false;

        else
            return true;

    }


}
