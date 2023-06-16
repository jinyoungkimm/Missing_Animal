package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


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


}
