package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportServiceImplTest {

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    ReportService reportService;


    @Test
    @Rollback(value = false)
    void saveReport() {

        //givien
        Long registerId = 1L;

        AddressForm addressForm = new AddressForm();
        addressForm.setDetailAdr("a");
        addressForm.setStreetAdr("b");
        addressForm.setZipcode("c");

        //when
        Long saveId = reportService.saveReport(registerId, addressForm);

        Report findReport = reportService.findOne(saveId);

        //then
        Member member = findReport.getMember();
        Register register = findReport.getRegister();

        Assertions.assertThat(member.getUsername()).isEqualTo("김진영1");
        Assertions.assertThat(register.getAnimalName()).isEqualTo("사랑이1");
        Assertions.assertThat(findReport.getFindedAddress().getZipcode()).isEqualTo("c");
        Assertions.assertThat(findReport.getFindedAddress().getStreetAdr()).isEqualTo("b");
        Assertions.assertThat(findReport.getFindedAddress().getDetailAdr()).isEqualTo("a");

    }

    @Test
    void findAll(){

        List<Report> allReports = reportService.findAllReports();

        for(int x = 0; x < allReports.size();x++){

            Report report = allReports.get(x);
            Assertions.assertThat(report.getId()).isEqualTo(x+1);

        }


    }


}