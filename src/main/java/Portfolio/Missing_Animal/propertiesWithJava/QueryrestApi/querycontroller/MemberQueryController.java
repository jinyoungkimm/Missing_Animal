package Portfolio.Missing_Animal.propertiesWithJava.QueryrestApi.querycontroller;


import Portfolio.Missing_Animal.propertiesWithJava.QueryrestApi.queryrepository.MemberQueryRepository;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.MemberDtoWithPagination;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController// @ResponseBody를 자동으로 붙여줌
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryRepository memberQueryRepository;


    //@GetMapping("")
    MemberDtoWithPagination getMembersInfo(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                   @RequestParam(value = "limit",defaultValue = "2") Integer limit){

            int pageNumber = (offset / limit) ; // limit != 0 이라는 보장이 있어야 한다. JPA는 PAGE가 0번부터 시작!

            int size = limit;

            MemberDtoWithPagination allWithPaging = memberQueryRepository.findAllWithPaging(pageNumber, size);

            return allWithPaging;

    }

    @GetMapping("")
    MemberDtoWithPagination getMembersInfoV2(@PageableDefault(page = 0,size = 2,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        System.out.println("page="+pageable.getPageNumber());

        MemberDtoWithPagination allWithPaging = memberQueryRepository.findAllWithPagingV2(pageable);

        return allWithPaging;

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
