package Portfolio.Missing_Animal.domainEntity;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString(of={"zipcode","streetAdr","detailAdr"})
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
