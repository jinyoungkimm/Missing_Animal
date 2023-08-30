package Portfolio.Missing_Animal.restAPI.APIcontroller;


import Portfolio.Missing_Animal.APIdto.*;
import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.restAPI.validation.ReportRequestDtoValidator;
import Portfolio.Missing_Animal.restAPI.validation.UpdateReportRequestValidator;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@LogTrace
public class ReportRestApiController {

    private final ReportService reportService;

    private final MemberService memberService;

    private final UpdateReportRequestValidator updateReportRequestValidator;

    private final ReportRequestDtoValidator reportRequestDtoValidator;


    @PostMapping("/new")
    Object createReport(@RequestBody ReportRequestDto reportRequestDto, BindingResult bindingResult){

        reportRequestDtoValidator.validate(reportRequestDto,bindingResult); // 직접 호출

        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();


        Report report = new Report();
        report.setFindedAddress(reportRequestDto.getFindedAddress());
        report.setFindedTime(LocalDateTime.now());

        // 입력된 id,pw가 등록된 것인지 아닌지 검증!
        Member member = new Member();
        String userId = reportRequestDto.getUserId();
        String password = reportRequestDto.getPassword();

        member.setUserId(userId);
        member.setPassword(password);
        memberService.login(member);

        // 이 시점에는 해당 member가 존재(Report와 연결된 Member 객체는 registerId로 찾아서 연결할 거임!)

        Long saveId = reportService.saveReport(member,reportRequestDto.getRegisterId(),report);

        if(saveId != null){

            ReportResponseDto updateReportResponse = new ReportResponseDto(saveId, true);
            return updateReportResponse;

        }
        else{

            ReportResponseDto updateReportResponse = new ReportResponseDto(null, false);
            return updateReportResponse;
        }

    }

    // 신고 내용 [수정] API
    @PostMapping("/edit")
    Object updateReport(@RequestBody UpdateReportRequest updateReportRequest, BindingResult bindingResult){

        updateReportRequestValidator.validate(updateReportRequest,bindingResult); // 직접 호출

        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();


        Report report = new Report();
        report.setFindedAddress(updateReportRequest.getFindedAddress());
        report.setFindedTime(LocalDateTime.now());

        Long updateId = reportService.updateReport(updateReportRequest.getReportId(), report);

        if(updateId != null){

            UpdateReportResponse updateReportResponse = new UpdateReportResponse(updateReportRequest.getReportId(), true);
            return updateReportResponse;

        }
        else{

            UpdateReportResponse updateReportResponse = new UpdateReportResponse(updateId, false);
            return updateReportResponse;
        }

    }

}
