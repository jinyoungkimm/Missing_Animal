package Portfolio.Missing_Animal.restAPI.APIcontroller;


import Portfolio.Missing_Animal.APIdto.UpdateReportRequest;
import Portfolio.Missing_Animal.APIdto.UpdateReportResponse;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportRestApiController {

    private final ReportService reportService;

    // 신고 내용 [수정] API
    @PostMapping("/{reportId}/edit")
    UpdateReportResponse updateReport(@PathVariable("reportId") Long reportId,
                                      @RequestBody UpdateReportRequest updateReportRequest){


        Report report = new Report();
        report.setAnimal(updateReportRequest.getAnimal());
        report.setFindedTime(updateReportRequest.getFindedTime());

        Long updateId = reportService.updateReport(reportId, report);

        if(updateId != null){

            UpdateReportResponse updateReportResponse = new UpdateReportResponse(reportId, true);
            return updateReportResponse;

        }
        else{

            UpdateReportResponse updateReportResponse = new UpdateReportResponse(reportId, false);
            return updateReportResponse;
        }

    }

}
