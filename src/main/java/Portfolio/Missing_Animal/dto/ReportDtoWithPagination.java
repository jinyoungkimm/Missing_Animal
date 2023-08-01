package Portfolio.Missing_Animal.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDtoWithPagination {

    private Pagination pagination;

    private List<ReportDto> ReportDtos;


    public ReportDtoWithPagination(Pagination pagination, List<ReportDto> ReportDtos) {

        this.pagination = pagination;

        this.ReportDtos = ReportDtos;

    }


    public ReportDtoWithPagination(){

    }




}
