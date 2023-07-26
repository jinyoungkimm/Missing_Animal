package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domainEntity.Report;

import java.util.List;

public interface ReportRepository {

    public Long save(Report report);

    public void delete(Report report);

    public long count();

    public Report findById(Long regiserId);

    public List<Report> findAll();


}
