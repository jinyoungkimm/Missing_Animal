package Portfolio.Missing_Animal.dto;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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

@Getter@Setter
@ToString(of = {"registerId","memberId","missingAddressId","animalName","animalSex","animalAge","registerDate","registerStatus","reportedStatus"})
public class RegisterDto {

    private Long registerId;
    @JsonIgnore
    private Long memberId;
    @JsonIgnore
    private Long missingAddressId;

    private String animalName;
    private String animalSex;
    private String animalAge;
    private LocalDateTime registerDate;
    private RegisterStatus registerStatus;

    private ReportedStatus reportedStatus;

    //컬렉션
    private List<ReportDto> reports;



   // private MemberDto member; // Member member x : DTO는 한 개라도 엔티티에 의존해서는 안된다.
    //private MissingAddressDto missingAddress; // MissingAddress missingAddress x : DTO는 한 개라도 엔티티에 의존해서는 안된다.

    // [페이징]을 사용허자 얺울 때, 사용하자!(Repository에서 new DTO와 Map을 사용
    //해서, 만들어 진다(Repository에서 이 생성자가 사용됨)
    public RegisterDto(Long registerId,Long memberId,Long missingAddressId, String animalName,String animalSex,String animalAge,LocalDateTime registerDate,RegisterStatus registerStatus,ReportedStatus reportedStatus) {

        this.registerId = registerId;
        this.memberId = memberId;
        this.missingAddressId = missingAddressId;
        this.animalName = animalName;
        this.animalSex = animalSex;
        this.animalAge =animalAge ;
        this.registerDate = registerDate;
        this.registerStatus = registerStatus;
        this.reportedStatus = reportedStatus;

         // 엔티티 -> DTO로 전환
        // this.member = new MemberDto(register.getMember());
        // this.missingAddress = new MissingAddressDto(register.getMissingAddress()); //이걸 제거하지 않으면, 무한루프에 빠짐!

    }


    public RegisterDto(Register register) { // [페이징]을 사용할 때 사용하자!(Defaul batch size로 인해 지연로딩된 컬렉션을 그냥 바로 넣으면 된다.)

        this.registerId = register.getId();
        this.animalName = register.getAnimalName();
        this.animalSex = register.getAnimalSex();
        this.animalAge = register.getAnimalAge();
        this.registerDate = register.getRegisterDate();
        this.registerStatus = register.getRegisterStatus();
        this.reportedStatus = register.getReportedStatus();

        /*this.reports = register.getReports().stream()

                .map(report -> new ReportDto(report))

                .collect(Collectors.toList());*/

    }

    public RegisterDto(){

    }
}
