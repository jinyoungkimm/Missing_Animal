package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;


    @GetMapping("")
    public String registerMissingGet(Model model){

        Register register = new Register();
        model.addAttribute("register",register);
        return "register";

    }

    @PostMapping("")
    public String registerMissingPost(Register register){

        registerService.registerMissing(register);


        return "redirect:/register";

    }
}
