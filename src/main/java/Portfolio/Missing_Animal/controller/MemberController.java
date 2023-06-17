package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    //회원 가입 기능
    @GetMapping("")
    public String Controller_join_Get(Model model){

        Member member = new Member();
        model.addAttribute("member",member);

        return "memberJoin";

    }

    @PostMapping("")
    public String Controller_join_Post(Member member){

        System.out.println(member);

        memberService.join(member);

        return "redirect:/member";
    }

    //로그인 기능
    @GetMapping("/login")
    public String Controller_LogIn_Get(Model model){


        model.addAttribute("memberId",new String());
        model.addAttribute("password",new String());

        return "members/loginForm";

    }

    @PostMapping("/login")
    public String Controller_LogIn_Post(HttpServletRequest request){

        System.out.println(request.getParameter("memberId"));
        System.out.println(request.getParameter("password"));

        return "redirect:/member/login";
    }


}
