package Portfolio.Missing_Animal.restAPI.APIcontroller;


import Portfolio.Missing_Animal.APIdto.*;
import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.restAPI.validation.LoginRequestDtoValidator;
import Portfolio.Missing_Animal.restAPI.validation.MemberRequestDtoValidator;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@LogTrace
public class MemberRestApiController {

    private final MemberService memberService;

    private final LoginRequestDtoValidator loginRequestDtoValidator;

    private final MemberRequestDtoValidator memberRequestDtoValidator;


    @PostMapping("/login") // 로그인 API
    Object loginApi(@RequestBody  LoginRequestDto loginRequest, BindingResult bindingResult)
    {
        loginRequestDtoValidator.validate(loginRequest,bindingResult); // 직접 호출

        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();

        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

        Member member = new Member();
        member.setUserId(userId);
        member.setPassword(password);

        Member login = memberService.login(member);

        LoginResponseDto loginResponse = new LoginResponseDto();
        if(login != null){

            loginResponse.setIsExist(true);

            return loginResponse;
        }

        else{

            loginResponse.setIsExist(false);

            return loginResponse;

        }

    }

    @PostMapping("/join") // 회원 등록 API
    Object joinApi(@RequestBody  MemberRequestDto memberRequestDto,BindingResult bindingResult){

        memberRequestDtoValidator.validate(memberRequestDto,bindingResult); // 직접 호출

        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();



        String userName = memberRequestDto.getUserName();
        String userId = memberRequestDto.getUserId();
        String password = memberRequestDto.getPassword();
        String phoneNumber = memberRequestDto.getPhoneNumber();

        Member member = new Member();
        member.setUsername(userName);
        member.setUserId(userId);
        member.setPassword(password);
        member.setPhoneNumber(phoneNumber);


        Long saveId = memberService.join(member);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(saveId);
        if(saveId != null)
            memberResponseDto.setComplete(true);

        else
            memberResponseDto.setComplete(false);

        return memberResponseDto;
    }

    // 회원 정보 [수정] API
    @PostMapping("/{memberId}/edit")
    UpdateMemberResponse updateMember(@PathVariable("memberId") Long memberId,
                                    @RequestBody UpdateMemberRequest updateMemberRequest){

        Member member = new Member();

        member.setUsername(updateMemberRequest.getUsername());
        member.setEmail(updateMemberRequest.getEmail());
        member.setBirthDate(updateMemberRequest.getBirthDate());
        member.setPhoneNumber(updateMemberRequest.getPhoneNumber());


        Long updateId = memberService.updateMember(memberId,member);

        if(updateId != null) {

            UpdateMemberResponse updateMemberResponse = new UpdateMemberResponse(memberId, true);
            return updateMemberResponse;
        }

        else{

            UpdateMemberResponse updateMemberResponse = new UpdateMemberResponse(memberId,false);
            return updateMemberResponse;
        }

    }
}
