package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepositorySDJ extends JpaRepository<Report,Long> {
}
