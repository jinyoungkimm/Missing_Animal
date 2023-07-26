package Portfolio.Missing_Animal.dto;

import lombok.Data;

import java.util.List;


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
