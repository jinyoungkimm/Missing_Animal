package Portfolio.Missing_Animal.QueryrestApi.queryrepository;

import Portfolio.Missing_Animal.dto.ReportDto;
import Portfolio.Missing_Animal.dto.ReportDtoWithPagination;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ReportQueryRepositoryTest {

    @Autowired
    ReportQueryRepository reportQueryRepository;

    @Autowired
    EntityManager em;

    @Test
    void findAll() {

        //when
        List<ReportDto> all = reportQueryRepository.findAll();

        //then
        for ( int x = 1; x <= all.size();x++) {

            ReportDto reportDto = all.get(x-1);
            System.out.println(reportDto.getReportId());
            assertThat(reportDto.getReportId()).isEqualTo(x);
        }

    }

    @Test
    void findAllWithPaging2(){

        ReportDtoWithPagination allWithPaging2 = reportQueryRepository.findAllWithPaging2(0, 2);
        System.out.println(allWithPaging2.getPagination());
        System.out.println(allWithPaging2.getReportDtos());

    }

    @Test
    void findById() {

        //givien
        Long reportId1 = 16L;
        Long reportId2 = 14L;
        Long reportId3 = 12L;

        //when
        ReportDto reportDto1 = reportQueryRepository.findById(reportId1);
        ReportDto reportDto2 = reportQueryRepository.findById(reportId2);
        ReportDto reportDto3 = reportQueryRepository.findById(reportId3);

        //then
        assertThat(reportDto1.getMemberId()).isEqualTo(4L);
        assertThat(reportDto1.getRegisterId()).isEqualTo(8L);

        assertThat(reportDto2.getMemberId()).isEqualTo(4L);
        assertThat(reportDto2.getRegisterId()).isEqualTo(7L);

        assertThat(reportDto3.getMemberId()).isEqualTo(3L);
        assertThat(reportDto3.getRegisterId()).isEqualTo(6L);
    }


    @Test
    void findAllWithPaging(){

        //givien
        int offset = 0;
        int limit = 5;

        //when
        List<ReportDto> allWithPaging = reportQueryRepository.findAllWithPaging(offset, limit);

        //then
        assertThat(allWithPaging.size()).isEqualTo(5);
        for(int x = 0; x <allWithPaging.size();x++){

            ReportDto reportDto = allWithPaging.get(x);
            assertThat(reportDto.getReportId()).isEqualTo(x+1);

        }
    }

    @Test
    void addressForm(){


        List<ReportDto> report = reportQueryRepository.findReport();
        for (ReportDto reportDto : report) {

            System.out.println(reportDto.getFindedAddress());
        }

    }

}