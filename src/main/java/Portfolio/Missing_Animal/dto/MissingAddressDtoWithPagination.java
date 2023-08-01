package Portfolio.Missing_Animal.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
