package Portfolio.Missing_Animal.domainEntity.animal;


import Portfolio.Missing_Animal.domainEntity.Register;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class Animal {

    @Id@GeneratedValue
    @Column(name = "animal_id")
    private Long id;


    @OneToMany(mappedBy = "animal")
    //@OneToMany
    private List<Register> registers = new ArrayList<>();


    public Animal(){

    }

}
