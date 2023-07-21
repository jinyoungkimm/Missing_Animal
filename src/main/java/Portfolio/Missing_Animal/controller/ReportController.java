package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.MissingAddressRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.ReportServiceImpl;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private final RegisterRepository registerRepository;

    private final MissingAddressRepository missingAddressRepository;

    @GetMapping("/{registerId}")
    String clickRegisterForReport(@RequestParam("registerId") Long registerId, Model model){

        Register register = registerRepository.findById(registerId);
        AddressForm findedAddress = new AddressForm();

        model.addAttribute("register",register);
        model.addAttribute("findedAddress",findedAddress);

        return "reports/report";

    }

    @PostMapping("/{registerId}")
    String report(@PathVariable("registerId") Long registerId,
                  @ModelAttribute AddressForm findedAddress){

        Long saveId = reportService.saveReport(registerId, findedAddress);


        return "redirecg:/";
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
