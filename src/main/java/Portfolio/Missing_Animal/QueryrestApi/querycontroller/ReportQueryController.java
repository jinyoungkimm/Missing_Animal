package Portfolio.Missing_Animal.QueryrestApi.querycontroller;

import Portfolio.Missing_Animal.dto.ReportDto;
import Portfolio.Missing_Animal.QueryrestApi.queryrepository.ReportQueryRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportQueryController {

    private final ReportQueryRepository reportQueryRepository;

    @GetMapping("")
    public List<ReportDto> allReport(){

        List<ReportDto> all = reportQueryRepository.findAll(); // 페이징 기능 x.

        //List<ReportDto> allWithPaging = reportQueryRepository.findAllWithPaging(offset,limit);// 페이징 기능 o

        return all;

    }

    @GetMapping("/{reportId}")
    public ReportDto getReportWithId(@PathVariable("reportId") Long reportId){

        try {
            ReportDto reportDto = reportQueryRepository.findById(reportId);

            return reportDto;
        }
        catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 Report가 2개 이상 조회됨");

        }
        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 Report가 조회되지 않음");

        }
    }

}
