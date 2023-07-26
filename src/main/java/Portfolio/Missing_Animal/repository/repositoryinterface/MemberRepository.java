package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domainEntity.Member;

import java.util.List;

public interface MemberRepository {

    public Long save(Member member);

    public void delete(Member member);

    public long count();

    public Member findById(Long id);

    public List<Member> findAll();

    public Member findByUserId(String userId);

    public List<Member> findByUserName(String username);



}
