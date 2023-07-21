package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportServiceImplTest {

    @Autowired
    ReportServiceImpl reportServiceImpl;


    @Test
    @Rollback(value = false)
    void showingRegisterContentById() {
        // Register 엔티티의 id : 1 ~ 8

        Report report = reportServiceImpl.showingRegisterContentById(1L,"wlsdud6523");

        System.out.println(report.getMember().getUsername());

        System.out.println(report.getRegister().getAnimalName());
        System.out.println(report.getRegister().getReportedStatus());


    }




}