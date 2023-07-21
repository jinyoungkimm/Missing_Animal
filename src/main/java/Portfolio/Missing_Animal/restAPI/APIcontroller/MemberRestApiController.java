package Portfolio.Missing_Animal.restAPI.APIcontroller;


import Portfolio.Missing_Animal.APIdto.LoginRequestDto;
import Portfolio.Missing_Animal.APIdto.LoginResponseDto;
import Portfolio.Missing_Animal.APIdto.MemberRequestDto;
import Portfolio.Missing_Animal.APIdto.MemberResponseDto;
import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memberApi")
@RequiredArgsConstructor
public class MemberRestApiController {

    private final MemberService memberService;

    @PostMapping("/login") // 로그인 API
    LoginResponseDto loginApi(@RequestBody LoginRequestDto loginRequest)
    {

        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

        Member member = new Member();
        member.setUserId(userId);
        member.setPassword(password);

        boolean login = memberService.login(member);

        LoginResponseDto loginResponse = new LoginResponseDto();
        if(login == true){

            loginResponse.setIsExist(true);

            return loginResponse;
        }

        else{

            loginResponse.setIsExist(false);

            return loginResponse;

        }

    }

    @PostMapping("/join") // 회원 등록 API
    MemberResponseDto joinApi(@RequestBody MemberRequestDto memberRequestDto){

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





}
