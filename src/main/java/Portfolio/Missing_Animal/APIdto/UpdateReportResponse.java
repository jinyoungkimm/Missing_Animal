package Portfolio.Missing_Animal.APIdto;

import lombok.Data;

@Data
public class UpdateReportResponse {

    Long reportId;

    Boolean complete;

    public UpdateReportResponse(Long reportId,Boolean complete){

        this.reportId = reportId;
        this.complete  = complete;

    }

    public UpdateReportResponse(){

    }

}
