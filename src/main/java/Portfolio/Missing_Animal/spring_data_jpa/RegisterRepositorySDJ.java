package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_custom.RegisterRepositorySDJCustom;
import Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_customImpl.RegisterRepositorySDJImpl;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface RegisterRepositorySDJ extends JpaRepository<Register,Long>, RegisterRepositorySDJCustom {

    // 쿼리 메서드 기능!
   /* public long countRegisterBy();

    public Register findRegisterById(Long id);

    public List<Register> findRegistersByAnimalName(String animalName);*/

    /**
     * [쿼리 메서드 기능]은 파라미터가 증가하면, 메서드 이름이 매우 지저분해진다.
     * 따라서, @Query에다가 JPQL을 직접 작성하여 메서드의 이름이 길어지는 것을 예방하자!
     *
     */
    public long countRegisterBy();

    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<Register> findAll(Pageable pageable);


    // toOne에 대해서는 fetch join, toMany에 대해서는 default batch size로 최적화!
    @Query("SELECT r FROM Register r LEFT JOIN FETCH r.member m" +
            " LEFT JOIN FETCH r.missingAddress ma" +
            " WHERE r.animalName LIKE concat('%',:animalName,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<Register> findByAnimalName(@Param("animalName") String animalName, Pageable pageable);

    // toOne에 대해서는 fetch join, toMany에 대해서는 default batch size로 최적화!
    @Query("SELECT r FROM Register r LEFT JOIN FETCH r.member m" +
            " LEFT JOIN FETCH r.missingAddress ma" +
            " WHERE r.id=:id")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Optional<Register> findById(@Param("id") Long id);

    // toOne에 대해서는 fetch join, toMany에 대해서는 default batch size로 최적화!
    @Query("SELECT r FROM Register r LEFT JOIN FETCH r.member m" +
            " LEFT JOIN FETCH r.missingAddress ma" +
            " WHERE r.animalName LIKE concat('%',:animalName,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public List<Register> findByAnimalName(@Param("animalName") String animalName);


}
