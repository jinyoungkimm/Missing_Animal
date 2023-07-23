package Portfolio.Missing_Animal.APIdto;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.animal.Animal;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UpdateReportRequest {

    Long reportId;

    AddressForm findedAddress;

    LocalDateTime findedTime;

    Animal animal;

}
