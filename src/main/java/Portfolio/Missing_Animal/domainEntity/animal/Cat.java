package Portfolio.Missing_Animal.domainEntity.animal;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value="cat")
@Getter
@Setter
public class Cat extends Animal{


}
