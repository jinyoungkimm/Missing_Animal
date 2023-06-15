package Portfolio.Missing_Animal;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Embeddable
public class AddressForm {

    private String zipcode;

    private String streetAdr;
    private String detailAdr;

    public AddressForm(){

    }

    public AddressForm(String zipcode, String streetAdr, String detailAdr) {

        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }

}
