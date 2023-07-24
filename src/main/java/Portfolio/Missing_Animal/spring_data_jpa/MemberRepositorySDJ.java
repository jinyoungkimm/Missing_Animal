package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepositorySDJ extends JpaRepository<Member,Long> {


    // 쿼리 메서드 기능!
   /* public long countMemberBy();

    public Member findMemberById(Long id);

    public Member findMemberByUserId(String userId);

    public List<Member> findMembersByUsername(String username);*/


    // @Query를 이용하여 메서드에 직접 JPQL 작성

    /**
     * [쿼리 메서드 기능]은 파라미터가 증가하면, 메서드 이름이 매우 지저분해진다.
     * 따라서, @Query에다가 JPQL을 직접 작성하여 메서드의 이름이 길어지는 것을 예방하자!
     *
     */
    public long countMemberBy();

    @Query("SELECT m FROM Member m WHERE m.id=:id")
    public Member findMemberById(@Param("id") Long id);

    @Query("SELECT m FROM Member m WHERE m.userId=:userId")
    public Member findMemberByUserId(@Param("userId") String userId);

    @Query("SELECT m FROM Member m WHERE m.username=:username")
    public List<Member> findMembersByUsername(@Param("username") String username);

}
