package Portfolio.Missing_Animal.domain;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.animal.Animal;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Report { // 발견 신고(Member와 Animal의 중간 Table 역할)

    @Id
    @GeneratedValue
    @Column(name="suspicious_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "register_id")
    private Register register;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    Member member; // 발견자 정보

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "animal_id")
    Animal animal; // 발견 동물의 종류!(ex. 개, 고양이...)


    @Embedded
    private AddressForm findedAddress;

    private LocalDateTime findedTime;

    public Report(){

    }


    // 연관 관계 메서드
    public void setMember(Member member){

        this.member = member;

        member.getReports().add(this);

    }

    public void setRegister(Register register){

        this.register = register;

        register.getReports().add(this);

    }




}
