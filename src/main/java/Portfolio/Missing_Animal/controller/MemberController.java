package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final RegisterService registerService;

    private final ReportService reportService;

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
    String mypage(Model model){ // cookie에 회원ID를 저장시켜서 userId를 받아서 아래의 내용들을 조회를 할 예정!

        // 회원 가입 시 기입한 내용 출력
        //memberService.memberInfo();

        // 해당 회원이 등록한 실종 정보가 있다면 출력
        //memberService.findRegiserInfo(userId);

        // 임의로 "wlsdud6523"라고 userId를 가정함
        Member findMember = memberService.memberInfo("wlsdud6523");
        List<Register> findRegisters = memberService.findRegiserInfo("wlsdud6523");
        memberService.findReportInfo("wlsdud6523");


        model.addAttribute("member",findMember);
        model.addAttribute("register",findRegisters);

        return "members/mypage";
    }

    // mypage의 [회원 정보] 수정 폼
    @GetMapping("/mypage/{id}/editMember")
    String mypageMemberUpdateGet(@PathVariable("id") Long id,Model model){


        Member member = memberService.findOne(id);
        model.addAttribute("member",member);

        return "members/mypage-MemberupdateForm";

    }

    @PostMapping("/mypage/{id}/editMember")
    String mypageMemberUpdatePost(Member member){

        memberService.updateMember(member.getId(), member);
        

        return "redirect:/member";


    }

    // mypage의 [실종 등록 정보] 수정 폼
    @GetMapping("/mypage/{id}/editRegister")
    String mypageRegisterUpdateGet(@PathVariable("id") Long id,Model model){


        Register register = registerService.findOne(id);
        model.addAttribute("register",register);

        return "members/mypage-RegisterupdateForm";

    }

    @PostMapping("/mypage/{id}/editRegister")
    String mypageRegisterUpdatePost(Register register){


        registerService.updateForm(register.getId(),register);

        return "redirect:/member";


    }
    // mypage의 [신고 내용 정보] 수정 폼
    @GetMapping("/mypage/{id}/editReport")
    String mypageReportUpdateGet(@PathVariable("id") Long id,Model model){


        Register register = registerService.findOne(id);
        model.addAttribute("register",register);

        return "members/mypage-RegisterupdateForm";

    }

    @PostMapping("/mypage/{id}/editReport")
    String mypageReportUpdatePost(Report report){

        reportService.updateReport(report.getId(),report);

        return "redirect:/member";


    }

}
