package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.annotation.Login;
import Portfolio.Missing_Animal.controller.validation.MemberValidator;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.restAPI.validation.LoginRequestDtoValidator;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
@LogTrace
public class MemberController {

    private static final String SESSION_ID="session Id";

    private final MemberService memberService;
    private final RegisterService registerService;

    private final ReportService reportService;

    private final MemberValidator memberValidator; // 검증 Validator를 주입

    private final LoginRequestDtoValidator loginRequestDtoValidator;

    /**
     * @Validated
     * -> @ModelAttribute 앞에 @Validated가 꼭 있어야 지만, memberValidator가 호출이 된다.
     * 근데 만약에 이 Controller에서 등록된 Validator는 memberValidator 1개 뿐이여서,
     * 아래에서  dataBinder.addValidators(memberValidator) 이거 하나만 등록해줘도 문제가 없다.
     * 근데 만약 Validator가 여러 개 있다면, @Validated는 WebDataBinder에서 등록된 Validator 중 어떤 것을 사용해야 할지를
     * 모른다.
     * 그때 우리가 오버라이딩한 Validator.supports()가 유용한 역할을 한다.
     * @ModelAttribbute의 클래스를 support의 매개변수로 넘긴 뒤에, WebDataBinder에 등록된 모든 Validator의 supprt()를 호출한다.
     * support 의 결과 타입에 맞는 Validator을 스프링이 선택을 한다.
     *
     */
    /*@InitBinder
    public void init(WebDataBinder dataBinder){

        dataBinder.addValidators(memberValidator); // Controller가 호출될 때마다 memberValidator가 새로 적용됨.
        dataBinder.addValidators(loginRequestDtoValidator);
    }*/


    //회원 가입 기능
    @GetMapping("/join")
    public String Controller_join_Get(Model model){

        Member member = new Member();
        model.addAttribute("member",member);

        return "members/memberJoin";

    }

    @PostMapping("/join")
    public String Controller_join_Post( @ModelAttribute Member member, BindingResult bindingResult){

        memberValidator.validate(member,bindingResult);

        if(bindingResult.hasErrors())
            return "members/memberJoin";

        Long memberId = memberService.join(member);
        if(memberId == null){

            bindingResult.reject("joinFail","이미 사용 중인 회원 ID입니다.");
            return "members/memberJoin";
        }

        return "redirect:/";
    }

    //로그인 기능
    @GetMapping("/login")
    public String Controller_LogIn_Get(@ModelAttribute LoginRequestDto loginRequestDto,Model model){ // 자동으로 model에 Member가 담긴다.

        model.addAttribute("loginRequestDto",loginRequestDto); // 이걸 만약에 생략하게 되면, {"loginRequestDto", member}가 자동으로 들어가 버림.

        return "members/loginForm";

    }

    /**
     * BindingResult는 반드시 @ModelAttribute 뒤에 적어야 한다.(@ModelAttribute에 의해서 객체가 생성되지 않으면, BindingResult 사용 불가)
     * BindingResult가 매개변수에 있으면, 클라이언트에서 [타입 에러]를 일으켜도 그 에러 정보를 BindingResult에 담아서 컨트롤러가 호출이 된다.
     * 그러나, 없다면, 4xx 에러를 클라이언트에게 던지면서 오류 페이지로 이동을 시킨다.
     */
    @PostMapping("/login")
    public String Controller_LogIn_Post( @ModelAttribute LoginRequestDto loginRequestDto,BindingResult bindingResult,
                                        @RequestParam(defaultValue = "/") String redirectURL,
                                        HttpServletRequest request) { // 서블릿이 제공하는 session 기능을 사용하기 위함!

        loginRequestDtoValidator.validate(loginRequestDto,bindingResult); // 직접 호출

        if (bindingResult.hasErrors()) { // 로그인 값 검증 에러

            log.info("errors={}", bindingResult);
            return "members/loginForm";

        }

        // 해당 id,pw가 등록 x
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();
        Member _login = new Member();
        _login.setUserId(userId);
        _login.setPassword(password);

        Member login = memberService.login(_login);
        if (login == null) // 로그인 실패
        {

                 bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다."); // 글로벌 오류

                 return "members/loginForm";
        }

         // 로그인 성공 시!
        HttpSession session = request.getSession(); // 신규 세션 생성이니, getSession(true)여야 하는데, getSession()의 디폴트 값은 true이다.
        session.setAttribute(SESSION_ID,login); // { "sessionId, Member객체 }로 Map 테이블이 생성.

        return "redirect:" + redirectURL;


    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false); // false : 새션이 없어도, 새로운 세션을 만들지 않고, null을 반환!

        if(session != null)
            session.invalidate(); // 세션 무효화!!! 세션 테이블에서 해당 세션 id와 객체가 사라짐.

        return "redirect:/";

    }


    @GetMapping("/mypage")
    String mypage(Model model,
                  @Login Member member,
                  @PageableDefault(page = 0,size = 2,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){ // cookie에 회원ID를 저장시켜서 userId를 받아서 아래의 내용들을 조회를 할 예정!

        String userId = member.getUserId();

        Member findMember = memberService.memberInfo(userId);

        model.addAttribute("member",findMember);



        Page<Register> registerPage = registerService.findRegiserInfo(userId,pageable);

        model.addAttribute("registerPage",registerPage);



        Page<Report> reportPage = reportService.findReportInfo(userId,pageable);

        int nowPage = reportPage.getPageable().getPageNumber() + 1; // or pageable.getPageNumber();
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,reportPage.getTotalPages());

        model.addAttribute("reportPage",reportPage);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);


        return "members/mypage";
    }

    @GetMapping("/{memberId}/getOneMember")
    String myPagegetOneMember(@PathVariable("memberId") Long memberId,Model model){

        Member findMember = memberService.findOne(memberId);

        if(findMember.getEmail() != null) {
            String email = findMember.getEmail().getFirst() + "@" + findMember.getEmail().getLast();
            findMember.getEmail().setFirst(email);
        }
        else{

            String email = "이메일에 대한 정보가 없습니다.";
            findMember.getEmail().setFirst(email);

        }

        model.addAttribute("member",findMember);

        return "members/showOneMember";

    }

    // mypage의 [회원 정보] 수정 폼
    @GetMapping("/mypage/{id}/editMember")
    String mypageMemberUpdateGet(@PathVariable("id") Long id,Model model){


        Member member = memberService.findOne(id);
        model.addAttribute("member",member);

        return "members/mypage-MemberupdateForm";

    }

    @PostMapping("/mypage/{id}/editMember")
    String mypageMemberUpdatePost(@ModelAttribute Member member,  RedirectAttributes redirectAttributes){


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
