package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.spring_data_jpa.ReportRepositorySDJ;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportRepositoryImplTest {

    /*@Autowired
    ReportRepository reportRepository;*/ // 순수 JPA Repository

    @Autowired
    ReportRepositorySDJ reportRepository; // Spring Data JPA Repository

    @Test
    void findById() {

        Report report = reportRepository.findById(1L).get();

        Assertions.assertThat(report.getMember().getUsername()).isEqualTo("김진영1");

    }



}