package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/mypage")
    String mypage(){ // cookie에 회원ID를 저장시켜서 userId를 받아서 아래의 내용들을 조회를 할 예정!

        // 회원 가입 시 기입한 내용 출력
        //memberService.memberInfo();


        // 해당 회원이 등록한 실종 정보가 있다면 출력
        //memberService.findRegiserInfo(userId);


        return "아직 만들지 않음";

    }
}
