package Portfolio.Missing_Animal.domainEntity.animal;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "dog")
@Getter
@Setter
public class Dog extends Animal{


}
