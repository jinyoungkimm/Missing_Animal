package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegisterRepositoryImpl implements RegisterRepository {

    private final EntityManager em;

    private final RegisterRepositorySDJ registerRepositorySDJ; // Spring Data + Querydsl

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

                        " LEFT JOIN FETCH r.member m" + // toOne은 모두 fetch join!
                        " LEFT JOIN FETCH r.missingAddress mr" +

                        " WHERE r.id=:id", Register.class)

                .setParameter("id", id)

                .getSingleResult();


    }

    @Override
    public List<Register> findAll() {

        return findRegisters();
    }

    @Override
    public Page<Register> findAllWithPaging(int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Register> page = registerRepositorySDJ.findAll(pageRequest);

        return page;

    }


    public List<Register> findRegisters() { // toOne 관계는 fetch join!

        return em.createQuery("SELECT r FROM Register r" +

                        " LEFT JOIN FETCH r.member m" +

                        " LEFT JOIN FETCH r.missingAddress mr"

                       ,Register.class)

                .getResultList();

    }

    public Page<Register> searchRegistersWithPagingComplexV2(RegisterSearchCond registerSearchCond, Pageable pageable){


        Page<Register> page = registerRepositorySDJ.searchRegistersWithPagingSimple(registerSearchCond, pageable);


        return page;

    }


    @Override
    public List<Register> findByAnimalName(String animalName) {

        return em.createQuery("SELECT r FROM Register r" +

                        " LEFT JOIN FETCH r.member m" + // toOne은 모두 fetch join으로!!

                        " LEFT JOIN FETCH r.missingAddress mr" +

                        " WHERE r.animalName LIKE concat('%',:animalName,'%')", Register.class)

                .setParameter("animalName",animalName)

                .getResultList();
    }

    @Override
    public Page<Register> ffindByAnimalNameWithPaging(String animalName, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Register> page = registerRepositorySDJ.findByAnimalName(animalName, pageRequest);

        return page;

    }


}
