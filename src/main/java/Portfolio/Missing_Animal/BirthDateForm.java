package Portfolio.Missing_Animal;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class BirthDateForm {

    private String year;
    private String moth;
    private String day;

}
