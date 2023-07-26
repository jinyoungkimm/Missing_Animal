package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.Member;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepositorySDJ extends JpaRepository<Member,Long> {


    // 쿼리 메서드 기능!

   /*

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
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    Page<Member> findByUsername(String username,Pageable pageable);
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    Page<Member> findAll(Pageable pageable);
    @Query("SELECT m FROM Member m WHERE m.id=:id")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Optional<Member> findById(@Param("id") Long id);

    @Query("SELECT m FROM Member m WHERE m.userId=:userId")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Member findByUserId(@Param("userId") String userId);

    @Query("SELECT m FROM Member m WHERE m.username=:username")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly",value = "true"))
    public List<Member> findByUsername(@Param("username") String username);



}
