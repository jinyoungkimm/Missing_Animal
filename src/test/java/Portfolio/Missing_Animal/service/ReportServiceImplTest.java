package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.domain.animal.Dog;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportServiceImplTest {

    @Autowired
    ReportService reportService;


    @Test
    //@Rollback(value = false)
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

        assertThat(member.getUsername()).isEqualTo("김진영1");
        assertThat(register.getAnimalName()).isEqualTo("사랑이1");
        assertThat(findReport.getFindedAddress().getZipcode()).isEqualTo("c");
        assertThat(findReport.getFindedAddress().getStreetAdr()).isEqualTo("b");
        assertThat(findReport.getFindedAddress().getDetailAdr()).isEqualTo("a");

    }

    @Test
    void findAll(){

        List<Report> allReports = reportService.findAllReports();

        for(int x = 0; x < allReports.size();x++){

            Report report = allReports.get(x);
            assertThat(report.getId()).isEqualTo(x+1);

        }


    }


    @Test
    //@Rollback(value = false)
    void updateReport(){

        //givien
        Long reportId = 2L;
        Report update = new Report();

        update.setAnimal(new Dog());
        LocalDateTime now = LocalDateTime.now();
        update.setFindedTime(now);
        update.setFindedAddress(new AddressForm("A","B","C"));

        //when
        reportService.updateReport(reportId,update);
        Report findReport = reportService.findOne(reportId);

        //then
        assertThat(findReport.getAnimal().getId()).isEqualTo(1L);
        assertThat(findReport.getFindedTime()).isEqualTo(now);
        assertThat(findReport.getFindedAddress().getZipcode()).isEqualTo("A");
        assertThat(findReport.getFindedAddress().getStreetAdr()).isEqualTo("B");
        assertThat(findReport.getFindedAddress().getDetailAdr()).isEqualTo("C");

    }


}