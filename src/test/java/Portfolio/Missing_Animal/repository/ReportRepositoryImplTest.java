package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.spring_data_jpa.ReportRepositorySDJ;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportRepositoryImplTest {

    @Autowired
    ReportRepositorySDJ reportRepository; // Spring Data JPA Repository

    @Test
    void findById() {

        Report report = reportRepository.findById(1L).get();

        assertThat(report.getMember().getUsername()).isEqualTo("김진영1");

    }


    @Test
    void findAllWithPaging(){

        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<Report> page = reportRepository.findAll(pageRequest);

        List<Report> content = page.getContent();

        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean hasNext = page.hasNext();

        //then
        for (Report report : content) {
            System.out.println("report = " + report);
        }
        assertThat(totalElements).isEqualTo(16L);
        assertThat(totalPages).isEqualTo(8L);


    }


}