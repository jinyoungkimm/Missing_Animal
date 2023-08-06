package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.Report;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReportRepositorySDJ extends JpaRepository<Report,Long> {

    /**
     * [쿼리 메서드 기능]은 파라미터가 증가하면, 메서드 이름이 매우 지저분해진다.
     * 따라서, @Query에다가 JPQL을 직접 작성하여 메서드의 이름이 길어지는 것을 예방하자!
     *
     */

    // toOne에 대해서는 fetch join! toMany에 대해서는 default batch size로 최적화!!!
    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.member m LEFT JOIN FETCH r.register rg WHERE r.id=:id")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Optional<Report> findById(@Param("id") Long id);

    // toOne에 대해서는 fetch join! toMany에 대해서는 default batch size로 최적화!!!
    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.member m LEFT JOIN FETCH r.register rg")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<Report> findAll(Pageable pageable);


    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.member m WHERE m.userId=:userId")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<Report> findByUserId(@Param("userId") String userId,Pageable pageable);
}
