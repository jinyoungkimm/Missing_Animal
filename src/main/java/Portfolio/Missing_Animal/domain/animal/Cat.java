package Portfolio.Missing_Animal.domain.animal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value="cat")
@Getter
@Setter
public class Cat extends Animal{


}
