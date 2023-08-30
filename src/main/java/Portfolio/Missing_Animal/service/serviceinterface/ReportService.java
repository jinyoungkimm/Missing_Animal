package Portfolio.Missing_Animal.service.serviceinterface;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {

    Long saveReport(Member member, Long registerId, Report report);

    Report findOne(Long reportId);

    List<Report> findAllReports();

    Long updateReport(Long reportId,Report report);


    Page<Report> findReportInfo(String userId, Pageable pageable);

}
