package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportRepository {

    public Long save(Report report);

    public void delete(Report report);

    public long count();

    public Report findById(Long regiserId);

    public List<Report> findAll();

    public Page<Report> findAllWithPaging(int pageNumber,int size);


    public Page<Report> findByUserId(String userId, Pageable pageable);
}
