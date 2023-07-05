package Portfolio.Missing_Animal.service.serviceinterface;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MemberService {

    // 회원 가입 서비스
    public void join(Member member);

    // 로그인 서비스
    public boolean login(Member member);

    public boolean isMemberExist(Member member);

    // 회원가입 시 등록한 정보를 출력
    public Member memberInfo(String userId);

    // 특정 회원이 등록한 실정 등록 정보가 있다면 출력
    public List<Register> findRegiserInfo(String userId);




}
