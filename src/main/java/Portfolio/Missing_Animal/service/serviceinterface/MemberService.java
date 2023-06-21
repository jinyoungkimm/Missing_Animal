package Portfolio.Missing_Animal.service.serviceinterface;

import Portfolio.Missing_Animal.domain.Member;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MemberService {

    // 회원 가입 서비스
    public void join(Member member);

    // 로그인 서비스
    public boolean login(Member member);


    public void validateDuplicateMember(Member member);

    public boolean isMemberExist(Member member);


}
