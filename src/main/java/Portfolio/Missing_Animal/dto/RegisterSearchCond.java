package Portfolio.Missing_Animal.dto;


import lombok.Data;

@Data
public class RegisterSearchCond {

    // 실종 등록 내용
    private String animalName;

    private String animalSex;

    private String animalAge;

    private String animalVariety;

    // 실종 주소
    private String prefecture; // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

    private String cityName; // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

    private String gu; // ex. 해운대구/기장군

    private String Dong; //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)

    private String streetName;// ex.[해빛로]

    private String streetNumber; // ex.해빛로-[11]

    private String zipcode;

    private String detailAdr;


}
