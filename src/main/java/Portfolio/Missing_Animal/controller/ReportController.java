package Portfolio.Missing_Animal.controller;

import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;

import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForReport;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.util.StringUtils.isEmpty;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private final StorageServiceForReport storageService;

    //private final RegisterRepository registerRepository; // 순수 JPA Repository

   // private final MissingAddressRepository missingAddressRepository; // 순수 JPA Repository

    private final RegisterRepositorySDJ registerRepository; // Spring Data JPA Repository

    private final MissingAddressRepositorySDJ missingAddressRepository;  // Spring Data JPA Repository



    @GetMapping("/{registerId}")
    String clickRegisterForReport(@PathVariable("registerId") Long registerId, Model model){

        Register register = registerRepository.findById(registerId).get();
        Member member = register.getMember();

        model.addAttribute("register",register); // 어떤 Register에 대한 신고인지에 대한 정보가 필요
        model.addAttribute("member",member); // 어떤 Member가 신고를 하는지에 대한 정보가 필요!
        Report report = new Report();
        model.addAttribute("report",report);

        return "reports/report";

    }

    @PostMapping("/{registerId}")
    String report(@PathVariable("registerId") Long registerId,
                  @ModelAttribute Report report,
                  @RequestParam("file") MultipartFile file){

        if(!file.isEmpty()) {

            storageService.store(file);
            String originalFilename = file.getOriginalFilename();
            report.setFileName(originalFilename);

        }

        Long saveId = reportService.saveReport(registerId, report);

        return "redirect:/";
    }

    @GetMapping("/{reportId}/getOneReport")
    String findOneReportById(@PathVariable("reportId") Long reportId,Model model){

        Report findReport = reportService.findOne(reportId);


        if(!isEmpty(findReport.getFileName()))
        {
            Path path = storageService.load(findReport.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadControllerForReport.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }

        model.addAttribute("report",findReport);
        return "reports/showOneReport";

    }


    @GetMapping("/{reportId}/edit")
    String updateReportGet(@PathVariable("reportId") Long reportId, Model model){

        Report findReport = reportService.findOne(reportId);

        System.out.println("findReport = " + findReport);

        model.addAttribute("report",findReport);

        return "reports/reportUpdate";

    }

    @PostMapping("/{reportId}/edit")
    String updateReportPost(Report report,
                            @PathVariable("reportId") Long reportId,
                            @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println("report = " + reportId);

        Report findReport = reportService.findOne(reportId);

        if(!file.isEmpty())
        {
            String fileName = findReport.getFileName();

            storageService.deleteOne(fileName); // 이전에 저장한 파일 삭제!

            storageService.store(file); // 새로 수정한 사진 저장!

        }

        String originalFilename = file.getOriginalFilename();

        report.setFileName(originalFilename);

        Long updateId = reportService.updateReport(reportId, report);

        return "redirect:/";

    }





}
