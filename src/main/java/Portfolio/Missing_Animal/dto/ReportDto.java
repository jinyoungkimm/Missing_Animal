package Portfolio.Missing_Animal.dto;


import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.domain.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ReportDto {

    Long reportId;
    @JsonIgnore
    Long registerId;

    @JsonIgnore
    Long memberId;



    public ReportDto(Long registerId){

        this.registerId = registerId;

    }

    public ReportDto(Report report){

        this.reportId = report.getId();

    }


    public ReportDto()
    {

    }
}
