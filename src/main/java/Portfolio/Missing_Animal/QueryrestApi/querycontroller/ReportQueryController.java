package Portfolio.Missing_Animal.QueryrestApi.querycontroller;

import Portfolio.Missing_Animal.QueryrestApi.queryrepository.ReportQueryRepository;
import Portfolio.Missing_Animal.dto.ReportDto;
import Portfolio.Missing_Animal.dto.ReportDtoWithPagination;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportQueryController {

    private final ReportQueryRepository reportQueryRepository;

    @GetMapping("")
    public ReportDtoWithPagination getAllReport(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "2") int limit){

        int pageNumber = (offset / limit) ; // limit != 0 이라는 보장이 있어야 한다. JPA는 PAGE가 0번부터 시작!
        int size = limit;

        ReportDtoWithPagination result = reportQueryRepository.findAllWithPaging2(pageNumber, size);


        return result;

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
