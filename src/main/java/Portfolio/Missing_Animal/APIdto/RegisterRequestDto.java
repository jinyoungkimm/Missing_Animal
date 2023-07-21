package Portfolio.Missing_Animal.APIdto;


import lombok.Data;

@Data
public class RegisterRequestDto {

    // 회원 정보
    private String userId;
    private String password;

    // 실종 등록 내용
    private String animalName;

    private String animalSex;

    private String animalAge;

    private String animalVariety;

    private String animalWeight;

    // 실종 주소
    private String zipcode;

    private String prefecture;

    private String cityName;

    private String gu;

    private String Dong;

    private String streetName;// ex.[해빛로]

    private String streetNumber; // ex.해빛로-[11]

}
