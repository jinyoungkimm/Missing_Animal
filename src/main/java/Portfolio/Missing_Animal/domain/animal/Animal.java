package Portfolio.Missing_Animal.domain.animal;


import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Animal {

    @Id@GeneratedValue
    @Column(name = "animal_id")
    private Long id;

    private String name;

    private int age;

    private double weight;

    //연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="register_id")
    private Register register;

    @OneToMany(mappedBy = "animal")
    List<Report> reports = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

}
