package Portfolio.Missing_Animal.restapi.querycontroller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.restapi.queryrepository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
    List<MemberDto> getMember(@PathVariable("userId") String userId){

        List<Member> memberWithUserId = memberQueryRepository.findMemberWithOneUserId(userId);

        List<MemberDto> collect = memberWithUserId.stream().

                map(m ->  new MemberDto(m))

                .collect(toList());

        return collect;
    }




}
