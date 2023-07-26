package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportRepositorySDJTest {


    @Autowired
    ReportRepositorySDJ reportRepository;


    @Test
    void basicCRUD(){

        //SAVE
        Report report1 = new Report();
        Report report2 = new Report();

        Report savedmember1 = reportRepository.save(report1);
        Report savedmember2 = reportRepository.save(report2);


        //findById(단건 조회)
        Report byId1 = reportRepository.findById(savedmember1.getId()).get();
        Report byId2 = reportRepository.findById(savedmember2.getId()).get();
        assertThat(byId1).isEqualTo(report1);
        assertThat(byId2).isEqualTo(report2);


        //findAll
        List<Report> all = reportRepository.findAll();
        assertThat(all.size()).isEqualTo(18L);


        //count
        long count = reportRepository.count();
        assertThat(count).isEqualTo(18L);


        //delete
        reportRepository.delete(report1);
        reportRepository.delete(report2);

        long deletedCount = reportRepository.count();
        assertThat(deletedCount).isEqualTo(16L);

    }

    @Test
    void joinFetch(){

        Report report = reportRepository.findById(1L).get();

        report.getMember().getUsername();
        report.getRegister().getAnimalName();

    }



}