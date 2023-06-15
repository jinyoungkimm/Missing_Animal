package Portfolio.Missing_Animal.domain;


import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class MemberAddress {

    private String zipcode;

    private String streetAdr;
    private String detailAdr;

    public MemberAddress(){

    }

    public MemberAddress(String zipcode, String streetAdr, String detailAdr) {

        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }



}
