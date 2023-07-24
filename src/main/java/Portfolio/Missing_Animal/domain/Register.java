package Portfolio.Missing_Animal.domain;


import Portfolio.Missing_Animal.domain.animal.Animal;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//@Data
@ToString(of={"id","fileName","animalName","animalSex","animalVariety","animalWeight","etc","registerDate","registerStatus","reportedStatus"})
public class Register { //실종 등록

    @Id@GeneratedValue
    @Column(name="register_id")
    private Long id;

    private String fileName;

    private String animalName;

    private String animalSex;

    private String animalAge;

    private String animalVariety;

    private String animalWeight;

    //그 외 특이사항
    private String etc;

    //실종 등록 날짜
    private LocalDateTime registerDate;

    // 실종 문제 해결 여부
    @Enumerated(EnumType.STRING) // [SOLVED,NOT_SOLVED]
    private RegisterStatus registerStatus;

    // 실종 등록에 대한 신고 여부
    @Enumerated(EnumType.STRING)
    private ReportedStatus reportedStatus;

    // 실종 등록 회원 정보
    //@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //실종된 동물의 종류(ex. 강아지, 고양이 등..)
    //@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;


    // 실종된 주소
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missing_address_id")
    private MissingAddress missingAddress;

    //신고 정보
    @OneToMany(mappedBy = "register" )
    private List<Report> reports = new ArrayList<>();

    public Register(){

    }


    // 연관 관계 메서드 : @ToOne 관계의 엔티티를 getter를 사용하여 호출하고, 거기에 Register 엔티티를 추가하자!!!
    // -> [수정,추가,삭제]에는 영향을 미치지는 않겠지만 Service 계층 등에서 Context에 있는 @toOne 엔티티를 캐싱하여 사용 가능!
    public void setMember(Member member){ // Register : Member = 다 : 1

        this.member = member; // Register가 연관관계 주인이므로, 여기에서만 Member가 저장된다.

        member.getRegisters().add(this); // 여기가 핵심임!

    }

    public void setMissingAddress(MissingAddress missingAddress){ // Register : MissingAddress = 다 : 1

        this.missingAddress = missingAddress; // Register가 연관관계 주인이므로, 여기에서만 MissingAddress가 저장된다.

        missingAddress.getRegisters().add(this); // 여기가 핵심임!

    }

    public void addReport(Report report) // Register : Report = 1 : 다
    {

        this.reports.add(report); // 여기가 핵심

        report.setRegister(this); // Report가 연관 관계 주인이므로, 이 코드에서 Register가 저장이 된다.

    }

}
