package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegisterRepositorySDJ extends JpaRepository<Register,Long> {

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

    @Query("SELECT r FROM Register r WHERE r.id=:id")
    public Register findRegisterById(@Param("id") Long id);
    @Query("SELECT r FROM Register r WHERE r.animalName=:animalName")
    public List<Register> findRegistersByAnimalName(@Param("animalName") String animalName);


}
