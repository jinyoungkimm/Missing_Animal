package Portfolio.Missing_Animal.QueryrestApi.querycontroller;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.QueryrestApi.queryrepository.MemberQueryRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
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


        List<MemberDto> allMembers = memberQueryRepository.findAllMembers4();

        return allMembers;

    }

    @GetMapping("/{userId}")
    MemberDto getMember(@PathVariable("userId") String userId){

        try {
            MemberDto member = memberQueryRepository.findMemberWithOneUserId2(userId);

            return member;

        }
        catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 회원이 2개이상 조회됨");

        }
        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 회원이 조회되지 않습니다.");

        }
    }

}
