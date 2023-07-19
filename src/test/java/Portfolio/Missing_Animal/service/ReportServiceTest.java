package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    ReportService reportService;


    @Test
    @Rollback(value = false)
    void showingRegisterContentById() {
        // Register 엔티티의 id : 1 ~ 8

        Report report = reportService.showingRegisterContentById(1L,"wlsdud6523");

        System.out.println(report.getMember().getUsername());

        System.out.println(report.getRegister().getAnimalName());
        System.out.println(report.getRegister().getReportedStatus());


    }




}