package Portfolio.Missing_Animal.restapi.queryrepository;


import Portfolio.Missing_Animal.domain.Register;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegisterQueryRepository {

    private final EntityManager em;


    public List<Register> findAllRegisters(){

        return em.createQuery("SELECT r FROM Register r" +
                " JOIN FETCH r.member m" +
                " JOIN FETCH r.missingAddress mr",Register.class)
                .getResultList();

    }

    public List<Register> findRegisterWithId(Long id){

        return em.createQuery("SELECT r FROM Register r" +
                " JOIN FETCH r.member m" +
                " JOIN FETCH r.missingAddress mr" +
                " WHERE r.id=:id",Register.class)
                .setParameter("id",id)
                .getResultList();
    }









}
