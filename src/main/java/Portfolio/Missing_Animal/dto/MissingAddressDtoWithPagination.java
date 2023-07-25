package Portfolio.Missing_Animal.dto;


import lombok.Data;

import java.util.List;

@Data
public class MissingAddressDtoWithPagination {

    private Pagination pagination;

    private List<MissingAddressDto> missingAddressDtos;


    public MissingAddressDtoWithPagination(Pagination pagination, List<MissingAddressDto> missingAddressDto) {

        this.pagination = pagination;

        this.missingAddressDtos = missingAddressDto;

    }



    public MissingAddressDtoWithPagination(){

    }

}
