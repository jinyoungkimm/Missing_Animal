package Portfolio.Missing_Animal.service.serviceinterface;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;

public interface ReportService {

    Long saveReport(Long registerId, AddressForm findedAddress);

    Report findOne(Long reportId);

    List<Report> findAllReports();

    Long updateReport(Long reportId,Report report);


}
