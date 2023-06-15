package Portfolio.Missing_Animal.domain;


import Portfolio.Missing_Animal.domain.animal.Animal;
import Portfolio.Missing_Animal.domain.animal.RegisterStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Register { //실종 등록

    @Id@GeneratedValue
    @Column(name="register_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Enumerated(EnumType.STRING)
    private RegisterStatus registerStatus;


    public Register(){

    }

    public Register(Long id, Member member, Animal animal) {

        this.id = id;
        this.member = member;
        this.animal = animal;

    }
}
