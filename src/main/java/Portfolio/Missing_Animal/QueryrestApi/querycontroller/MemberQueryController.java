package Portfolio.Missing_Animal.QueryrestApi.querycontroller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.QueryrestApi.queryrepository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryRepository memberQueryRepository;

    @GetMapping("")
    List<MemberDto> getMembersInfo(){

        /*List<Member> all = memberQueryRepository.findAllMembers();

        List<MemberDto> collect = all.stream()
                .map(m -> new MemberDto(m))
                .collect(toList());*/

        List<MemberDto> allMembers = memberQueryRepository.findAllMembers4();

        return allMembers;

    }

    @GetMapping("/{userId}")
    MemberDto getMember(@PathVariable("userId") String userId){

        Member member = memberQueryRepository.findMemberWithOneUserId(userId);

        MemberDto collect = new MemberDto(member);

        return collect;
    }




}
