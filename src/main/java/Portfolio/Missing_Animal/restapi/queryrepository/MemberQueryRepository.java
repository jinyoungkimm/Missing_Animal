package Portfolio.Missing_Animal.restapi.queryrepository;


import Portfolio.Missing_Animal.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;

    public List<Member> findAllMembers() {

    return em.createQuery("SELECT m FROM Member m" +
            " JOIN FETCH m.registers r" ,Member.class)
            .getResultList();
    }

    public List<Member> findMemberWithUserId(String userId) {

        return em.createQuery("SELECT m FROM Member m" +
                " JOIN FETCH m.registers r" +
                " WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getResultList();
    }


}
