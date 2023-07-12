package Portfolio.Missing_Animal.dto;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterDto {

    private Long id;

    private String animalName;

    private String animalSex;

    private String animalAge;

    private LocalDateTime registerDate;

    private RegisterStatus registerStatus;

    private ReportedStatus repotedStatus;

   // private MemberDto member; // Member member x : DTO는 한 개라도 엔티티에 의존해서는 안된다.
    //private MissingAddressDto missingAddress; // MissingAddress missingAddress x : DTO는 한 개라도 엔티티에 의존해서는 안된다.

    public RegisterDto(Register register) {

        this.id = register.getId();
        this.animalName = register.getAnimalName();
        this.animalSex = register.getAnimalSex();
        this.animalAge = register.getAnimalAge();
        this.registerDate = register.getRegisterDate();
        this.registerStatus = register.getRegisterStatus();
        this.repotedStatus = register.getRepotedStatus();

        // 엔티티 -> DTO로 전환
      //  this.member = new MemberDto(register.getMember());
       // this.missingAddress = new MissingAddressDto(register.getMissingAddress()); //이걸 제거하지 않으면, 무한루프에 빠짐!

    }

    public RegisterDto(){

    }
}
