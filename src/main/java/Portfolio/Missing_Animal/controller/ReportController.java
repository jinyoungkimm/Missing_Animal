package Portfolio.Missing_Animal.controller;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;

import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

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
                  @ModelAttribute Report report){

        Long saveId = reportService.saveReport(registerId, report);


        return "redirect:/";
    }

    @GetMapping("/{reportId}/getOneReport")
    String findOneReportById(@PathVariable("reportId") Long reportId,Model model){

        Report findReport = reportService.findOne(reportId);
        model.addAttribute("report",findReport);
        return "reports/showOneReport";

    }




}
