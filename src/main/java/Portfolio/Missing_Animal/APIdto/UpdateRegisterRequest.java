package Portfolio.Missing_Animal.APIdto;


import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Data;

@Data
public class UpdateRegisterRequest {


    // 수정 내용
    private Long registerId;

    private String animalName;

    private String animalSex;

    private String animalAge;

    private String animalVariety;

    private String animalWeight;

   // [SOLVED,NOT_SOLVED]
    private RegisterStatus registerStatus;

    // 실종 등록에 대한 신고 여부
    // [YES,NO]
    private ReportedStatus reportedStatus;


}
