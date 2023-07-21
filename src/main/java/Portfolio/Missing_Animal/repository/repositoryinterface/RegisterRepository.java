package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;

import java.util.List;

public interface RegisterRepository {

    public Long save(Register register);

    public Register findById(Long id);


    public List<Register> findAll();

    public List<Register> findByAnimalName(String animalName);


}
