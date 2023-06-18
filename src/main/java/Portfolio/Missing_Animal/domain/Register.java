package Portfolio.Missing_Animal.domain;


import Portfolio.Missing_Animal.domain.animal.Animal;
import Portfolio.Missing_Animal.domain.animal.RegisterStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Register { //실종 등록

    @Id@GeneratedValue
    @Column(name="register_id")
    private Long id;

    private String filePath;

    private String animalName;

    private String animal_sex;

    private String animal_age;

    private String animal_variety;

    private String animal_weight;

    //그 외 특이사항
    private String etc;

    //실종 등록 날짜
    private LocalDateTime registerDate;

    // 실종 문제 해결 여부
    @Enumerated(EnumType.STRING) // [SOLVED,NOT_SOLVED]
    private RegisterStatus registerStatus;

    // 실종 등록 회원 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //실종된 동물의 종류(ex. 강아지, 고양이 등..)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;


    // 실종된 주소
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missing_address_id")
    private MissingAddress missingAddress;




    public Register(){

    }

    public Register(Long id, String animal_sex, String animal_age, String animal_variety, String animal_weight, String etc, LocalDateTime registerDate, RegisterStatus registerStatus, Member member, Animal animal, MissingAddress missingAddress) {
        this.id = id;
        this.animal_sex = animal_sex;
        this.animal_age = animal_age;
        this.animal_variety = animal_variety;
        this.animal_weight = animal_weight;
        this.etc = etc;
        this.registerDate = registerDate;
        this.registerStatus = registerStatus;
        this.member = member;
        this.animal = animal;
        this.missingAddress = missingAddress;
    }
}
