package Portfolio.Missing_Animal.domainEntity;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
@Getter@Setter
@ToString(of={"id","zipcode","prefecture","cityName","gu","Dong","streetName","streetNumber"})
public class MissingAddress {

    @Id@GeneratedValue
    @Column(name="missing_address_id")
    private Long id;


    private String prefecture; // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

    private String cityName; // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

    private String gu; // ex. 해운대구/기장군

    private String Dong; //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)

    private String streetName;// ex.[해빛로]

    private String streetNumber; // ex.해빛로-[11]

    private String zipcode;

    @OneToMany(mappedBy = "missingAddress")
    private List<Register> registers = new ArrayList<>();

    // JPA는 반드시 [기본 생성자]를 필요로 한다.
    // @Data는 기본 생성자를 만들어 주지 않는 것에 주의!
    public MissingAddress(){

    }
    public MissingAddress(Long id, String zipcode, String city, String gu, String dong, String streetName, String streetNumber, List<Register> registers) {
        this.id = id;
        this.zipcode = zipcode;
        this.cityName = city;
        this.gu = gu;
        Dong = dong;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.registers = registers;
    }
}
