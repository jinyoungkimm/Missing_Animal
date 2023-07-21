package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domain.Report;

import java.util.List;

public interface ReportRepository {

    public Long save(Report report);

    public Report findById(Long regiserId);

    public List<Report> findAll();


}
