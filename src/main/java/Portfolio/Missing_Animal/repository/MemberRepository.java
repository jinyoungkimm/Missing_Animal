package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domain.Member;

import java.util.List;

public interface MemberRepository {

    public Long save(Member member);

    public Member findById(Long id);

    public List<Member> findAll();

    public List<Member> findByUserId(String userId);

    public List<Member> findByName(String username);



}
