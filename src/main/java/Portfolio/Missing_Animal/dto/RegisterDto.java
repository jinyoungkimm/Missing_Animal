package Portfolio.Missing_Animal.dto;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * [양방향] 관계의 엔티티(or DTO)를 되도록 만들지 말자
 *
 * 그 이유는
 *
 * 1. 순환 참조로인해 무한 루프 문제가 발생하기 쉽다.
 * -> 생성자를 여러 개 만들어서 [양방향] 관계여도 무한 루프를 안 걸리게 할 수도 있지만, 생성자가 너무 많게 되면 코드가 복잡해 진다.
 *
 * 2. [단방향] 관계의 엔티티(or DTO)를 만들돼, 절대 [다] 관계에 있는 엔티티(or DTO)에 다가 연관 관계 객체를 절대 선언하지는 말자!
 * -> 만약 RegisterDto -> MemberDto 방향으로 연관 관계(다:1)를 만들었다고 가정을 해보자.
 * 여기서 만약 2개의 RegisterDto가 있고, 그와 연관된 MemberDto가 1개가 있다고 하자.
 * RegisterDto는 List<RegisterDto>(size=2)로 만들어야 할 것이다.
 * 이때 2개의 RegisterDto는 각각 [똑같은] MemberDto를 가리켜야 하므로, 중복 데이터가 발생을 한다.
 *
 * 결론:
 * 엔티티(or DTO)는 되도록 [단방향]으로 만들고, API의 사용자에게 [연관 관계 객체의 ID값을 넘김으로써] 연관 관계 조회의 선택권을 넘기자!!
 */

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
        // this.member = new MemberDto(register.getMember());
        // this.missingAddress = new MissingAddressDto(register.getMissingAddress()); //이걸 제거하지 않으면, 무한루프에 빠짐!

    }

    public RegisterDto(){

    }
}
