package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.controller.validation.MemberValidator;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final RegisterService registerService;

    private final ReportService reportService;

    private final MemberValidator memberValidator; // 검증 Validator를 주입


    //회원 가입 기능
    @GetMapping("/join")
    public String Controller_join_Get(Model model){

        Member member = new Member();
        model.addAttribute("member",member);

        return "members/memberJoin";

    }

    @PostMapping("/join")
    public String Controller_join_Post(@ModelAttribute Member member,BindingResult bindingResult){


        memberValidator.validate(member,bindingResult);

       /* if(!StringUtils.hasText(member.getUsername()))
            bindingResult.rejectValue("username","required");

        if(!StringUtils.hasText(member.getUserId()))
            bindingResult.rejectValue("userId","required");

        if(!StringUtils.hasText(member.getPassword()))
            bindingResult.rejectValue("password","required");

        if(!StringUtils.hasText(member.getPhoneNumber()))
            bindingResult.rejectValue("phoneNumber","required");
        else{

            String phoneNumber = member.getPhoneNumber();
            try{

                Integer i = Integer.parseInt(phoneNumber);

            }
            catch (Exception e){

                bindingResult.rejectValue("phoneNumber","typeMismatch.phnoeNumber");

            }

        }
        if(!StringUtils.hasText(member.getEmail().getFirst()))
            bindingResult.rejectValue("email.first","required");

        if(!StringUtils.hasText(member.getEmail().getLast()))
            bindingResult.rejectValue("email.last","required");*/


        if(bindingResult.hasErrors())
            return "members/memberJoin";

        memberService.join(member);

        return "redirect:/";
    }

    //로그인 기능
    @GetMapping("/login")
    public String Controller_LogIn_Get(Model model){

        Member member = new Member();

        model.addAttribute("member",member);


        return "members/loginForm";

    }

    /**
     * BindingResult는 반드시 @ModelAttribute 뒤에 적어야 한다.(@ModelAttribute에 의해서 객체가 생성되지 않으면, BindingResult 사용 불가)
     * BindingResult가 매개변수에 있으면, 클라이언트에서 [타입 에러]를 일으켜도 그 에러 정보를 BindingResult에 담아서 컨트롤러가 호출이 된다.
     * 그러나, 없다면, 4xx 에러를 클라이언트에게 던지면서 오류 페이지로 이동을 시킨다.
     */
    @PostMapping("/login")
    public String Controller_LogIn_Post(@ModelAttribute Member member,BindingResult bindingResult,Model model){

        String userId = member.getUserId();
        String password = member.getPassword();


        memberValidator.validate(member,bindingResult);

       /* if(!StringUtils.hasText(userId)){

            bindingResult.rejectValue("userId","required");


        }

        if(!StringUtils.hasText(password)){

            bindingResult.rejectValue("password","required");

        }*/


        if(bindingResult.hasErrors()){ // 로그인 실패 시

            log.info("errors={}",bindingResult);
            model.addAttribute("member",member); // @ModelAttrobute는 자동으로 model에 들어가기 때문에, 사실 이 부분 생략 가능
            return "members/loginForm";

        }

        // 로그인 성공 시!
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
    String mypageMemberUpdatePost(@ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes){


        memberValidator.validate(member,bindingResult);

       /* if(!StringUtils.hasText(member.getUsername()))

        {
            bindingResult.rejectValue("username","required");
        }

        if(!StringUtils.hasText(member.getUserId()))
        {
            bindingResult.rejectValue("userId","required");
        }

        if(!StringUtils.hasText(member.getPassword()))
        {
            bindingResult.rejectValue("password", "required");
        }

        if(!StringUtils.hasText(member.getPhoneNumber())) {

                bindingResult.rejectValue("phoneNumber", "required");
        }

        else{

            String phoneNumber = member.getPhoneNumber();
            try{

                Integer i = Integer.parseInt(phoneNumber);

            }
            catch (Exception e){

                bindingResult.rejectValue("phoneNumber","typeMismatch.phnoeNumber");

            }

        }*/

        //회원 가입 실패 시
        if(bindingResult.hasErrors()){

            // model.addAttribute("member",member)은 생략 가능!
          return "members/mypage-MemberupdateForm";

        }


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
