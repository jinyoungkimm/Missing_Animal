package Portfolio.Missing_Animal.dto;


import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MissingAddressDto {

    private Long id;

    private String zipcode;

    private String prefecture; // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

    private String cityName; // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

    private String gu; // ex. 해운대구/기장군

    private String Dong; //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)

    private String streetName;// ex.[해빛로]

    private String streetNumber; // ex.해빛로-[11]

    private List<RegisterDto> registers = new ArrayList<>();

    public MissingAddressDto(MissingAddress missingAddress){


        this.id = missingAddress.getId();

        this.zipcode = missingAddress.getZipcode();

        this.prefecture = missingAddress.getPrefecture(); // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

        this.cityName = missingAddress.getCityName(); // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

        this.gu = missingAddress.getGu(); // ex. 해운대구/기장군

        this.Dong = missingAddress.getDong(); //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)

        this.streetName = missingAddress.getStreetName();// ex.[해빛로]

        this.streetNumber = missingAddress.getStreetNumber(); // ex.해빛로-[11]

        this.registers = new ArrayList<>();


    }
    public MissingAddressDto(){
        
    }


}
