package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.repository.MissingAddressRepository;
import Portfolio.Missing_Animal.service.ReportServiceImpl;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private final MissingAddressRepository missingAddressRepository;

    @GetMapping("")
    String reporting(@RequestParam("registerId") Long registerId,
                     @RequestParam("userId") String userId){ // userId : 신고자 ID

        reportService.showingRegisterContentById(registerId,userId);

        return "reports/report";

    }

    /*@GetMapping("~~~")
    List<RegisterDto> showAllRegisterWithMissingAddress(MissingAddress missingAddress){

        // 이 부분은 추후에 querydsl로 튜닝할 거임.
        //List<MissingAddress> m = missingAddressRepository.findByCityName(missingAddress);

        List<Register> registers = new ArrayList<>();
        // m.forEach( m -> List<Register> r = m.getRegister()
        //
        //   for(Regier register: r){
        //
        //     registers.add(register);
        //
        //   }
        //
        // )



        //return registers;
    }*/


}
