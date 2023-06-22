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

        System.out.println(register);

        // register 엔티티 저장(em.persist)시에, 연관 관계이 있는 Member,MissingAddress 등의 id 값을 모르면 등록(registerMissing)이 되지 않는다.
        // 고로, cascade(연쇄 반응).Persist를 설정하여, [register 엔티티를 영속화할 때, 연관된 엔티티도 자동으로 영속화 시켜줘야 한다.]
        registerService.registerMissing(register);


        return "redirect:/register";

    }
}
