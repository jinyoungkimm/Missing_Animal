package Portfolio.Missing_Animal.domain;


import Portfolio.Missing_Animal.domain.animal.Animal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @OneToMany(mappedBy = "register")
    List<Animal> animals = new ArrayList<>();




}
