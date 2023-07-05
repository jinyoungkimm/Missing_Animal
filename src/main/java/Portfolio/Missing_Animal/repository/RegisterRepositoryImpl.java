package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegisterRepositoryImpl implements RegisterRepository {

    private final EntityManager em;


    @Override
    public Long save(Register register) {

        em.persist(register);
        Long id = register.getId();
        return id;

    }

    @Override
    public Register findByAnimalId(Long id) {

        Register register = em.find(Register.class, id);
        return register;
    }



    @Override
    public List<Register> findAll() {

        return em.createQuery("SELECT r FROM Register r",Register.class)
                .getResultList();

    }

    @Override
    public List<Register> findByAnimalName(String animalName) {
        return em.createQuery("SELECT r FROM Register r" +
                        " WHERE r.animalName=:animalName", Register.class)
                .setParameter("animalName",animalName)
                .getResultList();
    }


}
