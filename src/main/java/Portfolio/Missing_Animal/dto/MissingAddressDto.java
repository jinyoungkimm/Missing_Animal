package Portfolio.Missing_Animal.dto;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 *  쿼리 Rest Api 생성 시, 연관 관계에 있는 엔티티를 반환하지 말고, 그 엔티티의 id를 반환하여
 *
 *  그 id값을 가지고 해당 엔티티를 다시 쿼리 Rest Api를 통해 조회하는 방식을 택하자!!
 *
 * 왜냐하면, 예를 들어, MissingAddress를 조회 시, Api 이용자는 Register에 대해서는 그렇게 궁금하지 않을 수가 있다.
 * 궁금한 경우에는 id 값이 주어 져 있으니, Api 이용자에게 Register 조회의 선택권을 넘긴다.
 */

@Getter@Setter
@ToString(of = {"missingAddressId","zipcode","prefecture","cityName","gu","Dong","streetName","streetNumber"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MissingAddressDto {

    private Long missingAddressId;

    private String zipcode;

    private String prefecture; // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

    private String cityName; // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

    private String gu; // ex. 해운대구/기장군

    private String Dong; //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)
    private String streetName;// ex.[해빛로]

    private String streetNumber; // ex.해빛로-[11]

    //private List<Long> registerIds = new ArrayList<>();

    private List<RegisterDto> registers = new ArrayList<>();

    public MissingAddressDto(Long missingAddressId,String zipcode,String prefecture,String cityName,String gu,String Dong,String streetName,String streetNumber){// [페이징]을 사용허자 얺울 때, 사용하자!(Repository에서 new DTO와 Map을 사용
        //해서, 만들어 진다(Repository에서 이 생성자가 사용됨)


        this.missingAddressId = missingAddressId;

        this.zipcode = zipcode;

        this.prefecture = prefecture; // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

        this.cityName =cityName; // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

        this.gu = gu; // ex. 해운대구/기장군

        this.Dong =Dong ; //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)

        this.streetName = streetName;// ex.[해빛로]

        this.streetNumber = streetNumber; // ex.해빛로-[11]

        /*this.registers = missingAddress.getRegisters()
                .stream()
                .map(r->new RegisterDto(r))
                .collect(Collectors.toList());*/
        /*List<Register> registers = missingAddress.getRegisters();
        for(Register r:registers)
        {
            this.registerIds.add(r.getId());

        }*/

    }

    public MissingAddressDto(MissingAddress missingAddress){ // [페이징]을 사용할 때 사용하자!(Defaul batch size로 인해 지연로딩된 컬렉션을 그냥 바로 넣으면 된다.)


        this.missingAddressId = missingAddress.getId();

        this.zipcode = missingAddress.getZipcode();

        this.prefecture = missingAddress.getPrefecture(); // ex. 충청남도, 전라남도( 존재할 수도 있고 안 할 수도 있음 )

        this.cityName = missingAddress.getCityName(); // 부산광역[시], 합천군 ( 존재할 수도 있고 안 할 수도 있음)

        this.gu = missingAddress.getGu(); // ex. 해운대구/기장군

        this.Dong = missingAddress.getDong(); //ex. [일광읍] (존재할 수도 있고 안 할 수도 있음)

        this.streetName = missingAddress.getStreetName();// ex.[해빛로]

        this.streetNumber = missingAddress.getStreetNumber(); // ex.해빛로-[11]

        //컬렉션
        this.registers = missingAddress.getRegisters().stream()

                .map(register-> new RegisterDto(register))

                .collect(Collectors.toList());
    }

    public MissingAddressDto(){
        
    }


}
