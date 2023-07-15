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

                " JOIN FETCH r.member m" + // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                " JOIN FETCH r.missingAddress mr" // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                        ,Register.class)
                .getResultList();

    }

    public List<Register> findRegisterWithId(Long id){

        return em.createQuery("SELECT r FROM Register r" +

                " JOIN FETCH r.member m" +// @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                " JOIN FETCH r.missingAddress mr" +// @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.

                " WHERE r.id=:id",Register.class)
                .setParameter("id",id)
                .getResultList();
    }









}
