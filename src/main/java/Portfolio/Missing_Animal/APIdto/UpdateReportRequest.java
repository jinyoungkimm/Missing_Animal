package Portfolio.Missing_Animal.APIdto;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domainEntity.animal.Animal;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UpdateReportRequest {


    AddressForm findedAddress;

    LocalDateTime findedTime;

    Animal animal;

}
