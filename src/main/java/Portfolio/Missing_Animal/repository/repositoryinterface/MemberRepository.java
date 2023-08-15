package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domainEntity.Member;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Long save(Member member);

    public void delete(Member member);

    public long count();

    public Member findById(Long id);

    public List<Member> findAll();

    public Page<Member> findAllWithPaging(int pageNumber,int size);

    public Member findByUserId(String userId);


    public List<Member> findByUserName(String username);

    public Page<Member> findByUserNameWithPaging(String username, int pageNumber,int size);


}
