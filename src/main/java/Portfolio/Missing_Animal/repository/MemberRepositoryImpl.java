package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;

    //CRUD
    @Override
    public Long save(Member member){

        em.persist(member);
        Long id = member.getId();
        return id;
    }
    @Override
    public Member findById(Long id){

        Member member = em.find(Member.class, id);

        return member;

    }
    @Override
    public List<Member> findAll(){

        return em.createQuery(
                "SELECT m from Member m",Member.class
        ).getResultList();

    }
    @Override
    public Member findByUserId(String userId) throws NoResultException, NonUniqueResultException {

        return em.createQuery("SELECT m FROM Member m WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getSingleResult();

    }
    @Override
    public List<Member> findByName(String username)
    {

        List<Member> username1 = em.createQuery("SELECT m FROM Member m WHERE m.username=:username", Member.class)
                .setParameter("username", username)
                .getResultList();

        return username1;

    }



}
