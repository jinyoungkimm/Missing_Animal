package Portfolio.Missing_Animal.domain.animal;


import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import jakarta.persistence.*;
import lombok.Getter;
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


    @OneToMany(mappedBy = "animal")
    //@OneToMany
    private List<Register> registers = new ArrayList<>();

}
