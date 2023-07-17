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

    /**
     * @xToOne 관계의 엔티티를 사용할지 안 할지는 모르지만 어차피 fetch join을 사용하면, 1번의 쿼리로 가져올 수 있으니, 일단은 가져 오도록 하자!
     * @return
     */
    public List<Register> findAllRegisters(){

        return em.createQuery("SELECT r FROM Register r" +

                " JOIN FETCH r.member m" + // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                " JOIN FETCH r.missingAddress mr" // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                        ,Register.class)
                .getResultList();

    }

    public List<Register> findRegistersWithPaging(int offset,int limit){

        return em.createQuery("SELECT r FROM Register r" +

                                " JOIN FETCH r.member m" + // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                                " JOIN FETCH r.missingAddress mr" // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                        ,Register.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();


    }

    public List<Register> findRegisterWithId(Long id,int offest,int limit){

        List<Register> id1 = em.createQuery("SELECT r FROM Register r" +

                        " JOIN FETCH r.member m" +// @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                        " JOIN FETCH r.missingAddress mr" +// @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.

                        " WHERE r.id=:id", Register.class)
                .setParameter("id", id)
                //  .setFirstResult(offest)
                //  .setMaxResults(limit)
                .getResultList();

        System.out.println("55555555555555555555555");

        return id1;



    }

}
