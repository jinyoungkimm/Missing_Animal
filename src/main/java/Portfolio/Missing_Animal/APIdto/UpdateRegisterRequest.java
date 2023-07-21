package Portfolio.Missing_Animal.APIdto;


import lombok.Data;

@Data
public class UpdateRegisterRequest {

    Long registerId;

    // 수정 내용
    private String animalName;

    private String animalSex;

    private String animalAge;

    private String animalVariety;

    private String animalWeight;


}
