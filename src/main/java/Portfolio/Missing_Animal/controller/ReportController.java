package Portfolio.Missing_Animal.controller;

import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.annotation.Login;
import Portfolio.Missing_Animal.controller.validation.ReportValidator;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;

import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForReport;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.ReportRepositorySDJ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.util.StringUtils.isEmpty;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
@LogTrace
@Slf4j
public class ReportController {

    private final ReportService reportService;

    private final StorageServiceForReport storageService;

    private final RegisterRepositorySDJ registerRepository; // Spring Data JPA Repository

    private final ReportRepositorySDJ reportRepository; // Spring Data JPA Repository

    private final MissingAddressRepositorySDJ missingAddressRepository;  // Spring Data JPA Repository

    private final ReportRepository _reportRepository;

    private final ReportValidator reportValidator;

  /*  @InitBinder
    public void init(WebDataBinder dataBinder){

        dataBinder.addValidators(reportValidator);

    }*/



    @GetMapping("/{registerId}")
    String clickRegisterForReport(@PathVariable("registerId") Long registerId, Model model){

        Register register = registerRepository.findById(registerId).get();

        Member member = register.getMember();
        String email = member.getEmail().getFirst() + "@" + member.getEmail().getLast();
        member.getEmail().setFirst(email);

        model.addAttribute("register",register); // 어떤 Register에 대한 신고인지에 대한 정보가 필요

        model.addAttribute("member",member); // 어떤 Member가 신고를 하는지에 대한 정보가 필요!
        Report report = new Report();
        model.addAttribute("report",report);

        return "reports/report";

    }

    @PostMapping("/{registerId}")
    String report(@PathVariable("registerId") Long registerId,
                  @Login Member _member,
                  @ModelAttribute Report report, BindingResult bindingResult,
                  @ModelAttribute Member member,
                  @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){

        reportValidator.validate(report,bindingResult);

        if(bindingResult.hasErrors())
            return "reports/report";


        if(!file.isEmpty()) {

            storageService.store(file);
            String originalFilename = file.getOriginalFilename();

            log.info("filename={}",originalFilename);

            report.setFileName(originalFilename);

        }



        Long saveId = reportService.saveReport(_member,registerId, report);

        redirectAttributes.addAttribute("status",true);

        return "redirect:/report/{registerId}";
    }

    @GetMapping("/{reportId}/getOneReport")
    String findOneReportById(@PathVariable("reportId") Long reportId,Model model){

        Report findReport = reportService.findOne(reportId);
        Member member = findReport.getMember();

        if(member.getEmail() != null) {

            String email = member.getEmail().getFirst() + "@" + member.getEmail().getLast();
            member.getEmail().setFirst(email);

        }
        else{

            String email = "이메일에 대한 정보가 없습니다.";
            member.getEmail().setFirst(email);
        }


        if(!isEmpty(findReport.getFileName()))
        {
            Path path = storageService.load(findReport.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadControllerForReport.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }

        model.addAttribute("report",findReport);
        model.addAttribute("member",member);
        return "reports/showOneReport";

    }

    @GetMapping("/{reportId}/getOneReportWithoutUpdate")
    String getOneReportWithoutUpdate(@PathVariable("reportId") Long reportId,Model model){

        Report findReport = reportService.findOne(reportId);
        Member member = findReport.getMember();
        String email = member.getEmail().getFirst() + "@" + member.getEmail().getLast();
        member.getEmail().setFirst(email);

        if(!isEmpty(findReport.getFileName()))
        {
            Path path = storageService.load(findReport.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadControllerForReport.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }

        model.addAttribute("report",findReport);
        model.addAttribute("member",member);
        return "reports/showOneReportWithoutUpdate";

    }

    @GetMapping("/{registerId}/getReports")
    String findReportsByRegisterId(@PathVariable("registerId") Long registerId,
                                   @PageableDefault(page = 0, size = 2, sort = "id",direction = Sort.Direction.ASC) Pageable pageable,
                                   Model model){
        System.out.println("registerId : " + registerId);

        Page<Report> page = reportRepository.findReportsByRegisterId(registerId, pageable);
        for (Report report : page) {
            System.out.println("report="+report);
        }

        model.addAttribute("registerId",registerId);
        model.addAttribute("page",page);

        int nowPage = page.getPageable().getPageNumber() + 1; // or pageable.getPageNumber();
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,page.getTotalPages());

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "reports/reportList";

    }


    @GetMapping("/{reportId}/edit")
    String updateReportGet(@PathVariable("reportId") Long reportId, Model model){

        Report findReport = reportService.findOne(reportId);

        System.out.println("findReport = " + findReport);

        model.addAttribute("report",findReport);

        return "reports/reportUpdate";

    }

    @PostMapping("/{reportId}/edit")
    String updateReportPost(@Validated @ModelAttribute Report report,BindingResult bindingResult,
                            @PathVariable("reportId") Long reportId,
                            @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {

        if(bindingResult.hasErrors())
            return "reports/reportUpdate";

        //Report findReport = reportService.findOne(reportId);
        Report findReport = _reportRepository.findById(reportId);

        if(!file.isEmpty())
        {
            String fileName = null;

            if(findReport.getFileName() != null) {

                fileName = findReport.getFileName();

                storageService.deleteOne(fileName); // 이전에 저장한 파일 삭제!

                storageService.store(file); // 새로 수정한 사진 저장!
            }
            else{

                storageService.store(file);

            }

        }

        String originalFilename = file.getOriginalFilename();

        report.setFileName(originalFilename);

        Long updateId = reportService.updateReport(reportId, report);


        redirectAttributes.addAttribute("status",true);

        return "redirect:/report/{reportId}/edit";

    }





}
