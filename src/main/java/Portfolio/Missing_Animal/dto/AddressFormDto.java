package Portfolio.Missing_Animal.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;


//@Entity
@Data
public class AddressFormDto {


   // @NotEmpty
    private String zipcode;
   // @NotEmpty
    private String streetAdr;
    private String detailAdr;

    public AddressFormDto(){

    }

    public AddressFormDto(String zipcode, String streetAdr, String detailAdr) {

        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }

}
