package Portfolio.Missing_Animal.dto;


import lombok.Data;

import java.util.List;

@Data
public class RegisterDtoWithPagination {


    private Pagination pagination;

    private List<RegisterDto> RegisterDtos;


    public RegisterDtoWithPagination(Pagination pagination, List<RegisterDto> RegisterDtos) {

        this.pagination = pagination;

        this.RegisterDtos = RegisterDtos;

    }


    public RegisterDtoWithPagination(){

    }
}
