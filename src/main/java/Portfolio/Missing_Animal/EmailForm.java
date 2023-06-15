package Portfolio.Missing_Animal;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Embeddable
@Data
public class EmailForm {

    private String first;

    private String last;

}
