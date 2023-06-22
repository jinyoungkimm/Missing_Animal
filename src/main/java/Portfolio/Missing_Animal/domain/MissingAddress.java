package Portfolio.Missing_Animal.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Data
public class MissingAddress {

    @Id@GeneratedValue
    @Column(name="missing_address_id")
    private Long id;

    private String zipcode;

    private String city;

    private String Gu;

    private String Dong;

    private String streetName;

    private String streetNumber;

    @OneToMany(mappedBy = "missingAddress")
    private List<Register> registers = new ArrayList<>();


}
