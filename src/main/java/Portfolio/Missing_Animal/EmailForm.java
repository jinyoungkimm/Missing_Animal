package Portfolio.Missing_Animal;


import jakarta.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class EmailForm {

    private String first;

    private String last;

}
