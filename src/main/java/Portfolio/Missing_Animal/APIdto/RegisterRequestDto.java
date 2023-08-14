package Portfolio.Missing_Animal.APIdto;



import lombok.Data;

@Data
public class RegisterRequestDto {

    // 회원 정보
    //@NotBlank(message = "userId는 필수 입력값입니다.")
    private String userId;

   // @NotBlank(message = "password는 필수 입력 값입니다.")
    private String password;

    // 실종 등록 내용
   // @NotBlank(message = "animalName는 필수 입력 값입니다.")
    private String animalName;

   // @NotBlank(message = "animalSex는 필수 입력 값입니다.")
    private String animalSex;

   // @NotBlank(message = "animalAge는 필수 입력 값입니다.")
    private String animalAge;

   // @NotBlank(message = "animalVariety는 필수 입력 값입니다.")
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
