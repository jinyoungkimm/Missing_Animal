package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepositorySDJ extends JpaRepository<Member,Long> {


    // 쿼리 메서드 기능!
    public long countMemberBy();

    public Member findMemberById(Long id);

    public Member findMemberByUserId(String userId);

    public List<Member> findMembersByUsername(String username);

}
