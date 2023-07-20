package Portfolio.Missing_Animal.restapi.querycontroller;

import Portfolio.Missing_Animal.dto.ReportDto;
import Portfolio.Missing_Animal.restapi.queryrepository.ReportQueryRepository;
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

        List<ReportDto> all = reportQueryRepository.findAll();

        return all;


    }

    @GetMapping("/{reportId}")
    public ReportDto getReportWithId(@PathVariable("reportId") Long reportId){


        ReportDto reportDto = reportQueryRepository.findById(reportId);

        return reportDto;


    }

}
