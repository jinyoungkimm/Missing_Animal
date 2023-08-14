package Portfolio.Missing_Animal.APIdto;

import lombok.Data;

@Data
public class ReportResponseDto {

    Long reportId;

    Boolean complete;

    public ReportResponseDto(Long reportId,Boolean complete){

        this.reportId = reportId;
        this.complete = complete;
    }

}
