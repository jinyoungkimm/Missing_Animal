package Portfolio.Missing_Animal.dto;


import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.domain.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {

    Long reportId;
    @JsonIgnore
    Long registerId;

    @JsonIgnore
    Long memberId;

    String finderUserId;

    LocalDateTime findedTime;

    public ReportDto(Long reportId,Long registerId,Long memberId,String finderUserId,LocalDateTime findedTime){

        this.reportId = memberId;
        this.registerId = registerId;
        this.memberId = memberId;
        this.finderUserId = finderUserId;
        this.findedTime = findedTime;

    }

    public ReportDto(Report report){

        this.reportId = report.getId();

    }


    public ReportDto()
    {

    }
}
