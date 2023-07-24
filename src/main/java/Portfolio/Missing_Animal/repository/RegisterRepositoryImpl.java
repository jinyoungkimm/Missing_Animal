package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.dto.RegisterDto;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void delete(Register register){

        em.remove(register);

    }

    @Override
    public long count(){

        return em.createQuery("SELECT count(r) FROM Register r",Long.class)
                .getSingleResult();

    }

    @Override
    public Register findById(Long id) {

        return em.createQuery("SELECT r FROM Register r" +

                        " JOIN FETCH r.member m" + // toOne은 모두 fetch join!
                        " JOIN FETCH r.missingAddress mr" +

                        " WHERE r.id=:id", Register.class)

                .setParameter("id", id)

                .getSingleResult();


    }

    @Override
    public List<Register> findAll() {

        return findRegisters();
    }


    public List<Register> findRegisters() { // toOne 관계는 fetch join!

        return em.createQuery("SELECT r FROM Register r" +

                        " JOIN FETCH r.member m" +

                        " JOIN FETCH r.missingAddress mr"

                       ,Register.class)

                .getResultList();

    }



    @Override
    public List<Register> findByAnimalName(String animalName) {

        return em.createQuery("SELECT r FROM Register r" +

                        " JOIN FETCH r.member m" + // toOne은 모두 fetch join으로!!

                        " JOIN FETCH r.missingAddress mr" +

                        " WHERE r.animalName=:animalName", Register.class)

                .setParameter("animalName",animalName)

                .getResultList();
    }


}
