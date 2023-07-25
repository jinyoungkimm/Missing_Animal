package Portfolio.Missing_Animal.dto;

import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.domain.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class MemberDtoWithPagination {

    private Pagination pagination;

    private List<MemberDto> memberDtos;


    public MemberDtoWithPagination(Pagination pagination, List<MemberDto> memberDtos) {

        this.pagination = pagination;

        this.memberDtos = memberDtos;

    }



    public MemberDtoWithPagination(){

    }
}
