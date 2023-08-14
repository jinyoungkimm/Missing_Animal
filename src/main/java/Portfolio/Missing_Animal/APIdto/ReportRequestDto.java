package Portfolio.Missing_Animal.APIdto;


import Portfolio.Missing_Animal.AddressForm;
import lombok.Data;

@Data
public class ReportRequestDto {

    String userId;
    String password;


    Long registerId;


    AddressForm findedAddress;


}
