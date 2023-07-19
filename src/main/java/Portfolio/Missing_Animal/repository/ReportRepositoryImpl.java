package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final EntityManager em;

    public Long save(Report report){

        em.persist(report);

        Long id = report.getId();

        return id;

    }
}
