package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final EntityManager em;

    @Override
    public Long save(Report report){

        em.persist(report);

        Long id = report.getId();

        return id;

    }

    @Override
    public void delete(Report report){

        em.remove(report);

    }

    @Override
    public long count(){

        return em.createQuery("SELECT count(r) FROM Repor r",Long.class)
                .getSingleResult();

    }


    @Override
    public Report findById(Long reportId) throws NonUniqueResultException, NoResultException {

        return em.createQuery("SELECT r FROM Report r" +

                " JOIN FETCH r.member m" + // toOne은 default batch size가 적용이 안되므로, fetch join으로 가져온다.
                " JOIN FETCH r.register rg" +

                " WHERE r.id=:id",Report.class)
                .setParameter("id",reportId)
                .getSingleResult();

    }

    @Override
    public List<Report> findAll() {

        return findReports();

    }

    public List<Report> findReports(){ // toOne 관계를 모두 fetch join

        return em.createQuery("SELECT r FROM Report r" +

                        " JOIN FETCH r.member m" + // toOne 은 모두 fetch join 사용!
                        " JOIN FETCH r.register rg",Report.class)

                .getResultList();


    }


}
