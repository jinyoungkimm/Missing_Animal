package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final RegisterService registerService;

    private final ReportService reportService;



    //회원 가입 기능
    @GetMapping("/join")
    public String Controller_join_Get(Model model){

        Member member = new Member();
        model.addAttribute("member",member);

        return "members/memberJoin";

    }

    @PostMapping("/join")
    public String Controller_join_Post(Member member){


        memberService.join(member);

        return "redirect:/";
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
    String mypage(Model model,
                  @PageableDefault(page = 0,size = 2,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){ // cookie에 회원ID를 저장시켜서 userId를 받아서 아래의 내용들을 조회를 할 예정!


        // 임의로 "wlsdud6523"라고 userId를 가정함
        Member findMember = memberService.memberInfo("wlsdud6523");

        model.addAttribute("member",findMember);



        Page<Register> registerPage = registerService.findRegiserInfo("wlsdud6523",pageable);

        model.addAttribute("registerPage",registerPage);



        Page<Report> reportPage = reportService.findReportInfo("wlsdud6523",pageable);

        int nowPage = reportPage.getPageable().getPageNumber() + 1; // or pageable.getPageNumber();
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,reportPage.getTotalPages());

        model.addAttribute("reportPage",reportPage);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);


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
    String mypageMemberUpdatePost(Member member, RedirectAttributes redirectAttributes){

        memberService.updateMember(member.getId(), member);

        redirectAttributes.addAttribute("status",true);
        redirectAttributes.addAttribute("id",member.getId());

        return "redirect:/member/mypage/{id}/editMember";


    }

    // mypage의 [실종 등록 정보] 수정 폼
    @GetMapping("/mypage/{id}/editRegister")
    String mypageRegisterUpdateGet(@PathVariable("id") Long id,Model model){


        Register register = registerService.findOne(id);
        model.addAttribute("register",register);

        return "members/mypage-RegisterupdateForm";

    }

    @PostMapping("/mypage/{id}/editRegister")
    String mypageRegisterUpdatePost(Register register,RedirectAttributes redirectAttributes){


        registerService.updateForm(register.getId(),register);

        redirectAttributes.addAttribute("id",register.getId());
        redirectAttributes.addAttribute("status",true);

        return "redirect:/member/mypage/{id}/editRegister";


    }
    // mypage의 [신고 내용 정보] 수정 폼
    @GetMapping("/mypage/{id}/editReport")
    String mypageReportUpdateGet(@PathVariable("id") Long id,Model model){


        Register register = registerService.findOne(id);
        model.addAttribute("register",register);

        return "members/mypage-RegisterupdateForm";

    }

    @PostMapping("/mypage/{id}/editReport")
    String mypageReportUpdatePost(Report report,RedirectAttributes redirectAttributes){

        reportService.updateReport(report.getId(),report);


        redirectAttributes.addAttribute("id",report.getId());
        redirectAttributes.addAttribute("status",true);

        return "redirect:/member/mypage/{id}/editReport";


    }

}
