package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepositorySDJ extends JpaRepository<Register,Long> {

    // 쿼리 메서드 기능!
    public long countRegisterBy();

    public Register findRegisterById(Long id);

    public List<Register> findRegistersByAnimalName(String animalName);


}
