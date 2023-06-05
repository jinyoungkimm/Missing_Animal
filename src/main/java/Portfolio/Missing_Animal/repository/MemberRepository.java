package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //CRUD
    public Long save(Member member){

        em.persist(member);
        Long id = member.getId();
        return id;
    }

    public Member findById(Long id){

        Member member = em.find(Member.class, id);
        return member;

    }

    public List<Member> findAll(){

        return em.createQuery(
                "SELECT m from Member m",Member.class
        ).getResultList();

    }

    public List<Member> findByName(String username){

        return em.createQuery("SELECT m FROM Member m WHERE m.username=:username",Member.class)
                .setParameter("username",username)
                .getResultList();

    }



}
