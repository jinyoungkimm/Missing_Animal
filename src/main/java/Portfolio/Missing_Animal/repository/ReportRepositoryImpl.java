package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import Portfolio.Missing_Animal.spring_data_jpa.ReportRepositorySDJ;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Primary
@Qualifier("mainReportRepositoryImpl")
public class ReportRepositoryImpl implements ReportRepository {

    private final EntityManager em;

    private final ReportRepositorySDJ reportRepositorySDJ; // Spring Data JPA


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

                " LEFT JOIN FETCH r.member m" + // toOne은  fetch join으로 가져온다.
                " LEFT JOIN FETCH r.register rg" +

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

                        " LEFT JOIN FETCH r.member m" + // toOne 은 모두 fetch join 사용!
                        " LEFT JOIN FETCH r.register rg",Report.class)

                .getResultList();


    }

    @Override
    public Page<Report> findAllWithPaging(int pageNumber,int size) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Report> page = reportRepositorySDJ.findAll(pageRequest);

        return page;

    }

    @Override
    public Page<Report> findByUserId(String userId, Pageable pageable) {

        Page<Report> reports = reportRepositorySDJ.findByUserId(userId, pageable);

        return reports;
    }

}
