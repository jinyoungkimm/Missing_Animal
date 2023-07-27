package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.spring_data_jpa.MemberRepositorySDJ;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;
    private final MemberRepositorySDJ memberRepositorySDJ; // [Spring Data JPA]


    //CRUD
    @Override
    public Long save(Member member){

        em.persist(member);
        Long id = member.getId();
        return id;
    }

    @Override
    public void delete(Member member){

        em.remove(member);

    }

    @Override
    public long count() {

        return em.createQuery("SELECT count(m) FROM Member m",Long.class)

                .getSingleResult();

    }

    @Override
    public Member findById(Long id){

      return em.createQuery("SELECT m FROM Member m" +
              " WHERE m.id=:id",Member.class)
              .setParameter("id",id)
              .getSingleResult();

    }
    @Override
    public List<Member> findAll(){

        return em.createQuery(
                "SELECT m from Member m",Member.class
        ).getResultList();

    }

    @Override
    public Page<Member> findAllWithPaging(int pageNumber,int size){ // Spring Data JPA 이용

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<Member> page = memberRepositorySDJ.findAll(pageRequest);

        return page;

    }


    @Override
    public Member findByUserId(String userId) throws NoResultException, NonUniqueResultException {

        return em.createQuery("SELECT m FROM Member m WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getSingleResult();

    }
    @Override
    public List<Member> findByUserName(String username)
    {

        List<Member> findusername = em.createQuery("SELECT m FROM Member m WHERE m.username LIKE concat('%',:username,'%')", Member.class)
                .setParameter("username", username)
                .getResultList();

        return findusername;

    }

    @Override
    public Page<Member> findByUserNameWithPaging(String username,int pageNumber,int size){ // Spring Data JPA 이용


        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<Member> page = memberRepositorySDJ.findByUsername(username, pageRequest);

        return page;

    }


}
