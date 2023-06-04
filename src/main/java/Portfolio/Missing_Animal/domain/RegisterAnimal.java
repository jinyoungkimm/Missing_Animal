package Portfolio.Missing_Animal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RegisterAnimal {

    @Id
    @GeneratedValue
    @Column(name="register_animal_id")
    private Long id;



}
