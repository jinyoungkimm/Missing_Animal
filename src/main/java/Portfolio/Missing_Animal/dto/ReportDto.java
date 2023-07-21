package Portfolio.Missing_Animal.dto;

import Portfolio.Missing_Animal.AddressForm;
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

    LocalDateTime findedTime;

    AddressForm findedAddress;

    public ReportDto(Long reportId,Long registerId,Long memberId,LocalDateTime findedTime,AddressForm findedAddress){

        this.reportId = reportId;
        this.registerId = registerId;
        this.memberId = memberId;
        this.findedTime = findedTime;
        this.findedAddress = findedAddress;

    }

    public ReportDto(Report report){

        this.reportId = report.getId();
        this.findedTime = report.getFindedTime();

    }


    public ReportDto()
    {

    }
}
