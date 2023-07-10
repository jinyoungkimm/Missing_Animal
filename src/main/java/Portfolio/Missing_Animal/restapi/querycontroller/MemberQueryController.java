package Portfolio.Missing_Animal.restapi.querycontroller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.restapi.queryrepository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController("/api/members") // @ResponseBody를 자동으로 붙여줌
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryRepository memberQueryRepository;

    @GetMapping("")
    List<MemberDto> getMembersInfo(){

        List<Member> all = memberQueryRepository.findAllMembers();

        List<MemberDto> collect = all.stream()
                .map(m -> new MemberDto(m))
                .collect(toList());

        return collect;

    }

    @GetMapping("/{userId}")
    MemberDto getMember(@PathVariable("userId") String userId){

        List<Member> memberWithUserId = memberQueryRepository.findMemberWithUserId(userId);

        List<MemberDto> collect = memberWithUserId.stream().
                map(m -> new MemberDto(m))
                .collect(toList());

        return collect.get(0);
    }

}
