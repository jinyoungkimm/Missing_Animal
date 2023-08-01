package Portfolio.Missing_Animal.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolvedIncidentDto {


    Integer allRegistersCount;

    Integer solvedRegistersCount;

}
