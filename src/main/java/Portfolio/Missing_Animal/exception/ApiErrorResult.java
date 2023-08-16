package Portfolio.Missing_Animal.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResult {

    private String code;
    private String message;

}
