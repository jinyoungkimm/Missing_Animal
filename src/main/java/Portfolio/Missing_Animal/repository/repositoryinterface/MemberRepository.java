package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Long save(Member member);

    public void delete(Member member);

    public long count();

    public Member findById(Long id);

    public List<Member> findAll();

    public Member findByUserId(String userId);

    public List<Member> findByUserName(String username);



}
