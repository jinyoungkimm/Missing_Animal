package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Member;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MemberService {

    public void join(Member member);


    public void validateDuplicateMember(Member member);


}
