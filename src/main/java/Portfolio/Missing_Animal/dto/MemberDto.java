package Portfolio.Missing_Animal.dto;


import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  쿼리 Rest Api 생성 시, 연관 관계에 있는 엔티티를 반환하지 말고, 그 엔티티의 id를 반환하여
 *
 *  그 id값을 가지고 해당 엔티티를 다시 쿼리 Rest Api를 통해 조회하는 방식을 택하자!!
 *
 * 왜냐하면, 예를 들어, Member를 조회 시, Api 이용자는 Register에 대해서는 그렇게 궁금하지 않을 수가 있다.
 * 궁금한 경우에는 id 값이 주어 져 있으니, Api 이용자에게 Register 조회의 선택권을 넘긴다.
 */

@Data
public class MemberDto {
    private Long id;
    private String userId;
    private String username;
    private EmailForm email;
    private String phoneNumber;

    // 컬렉션
    private List<RegisterDto> registers = new ArrayList<>();

    public MemberDto(Long id,String userId,String username,EmailForm email,String phoneNumber) { // [페이징]을 사용허자 얺울 때, 사용하자!(Repository에서 new DTO와 Map을 사용
                                                                                                            //해서, 만들어 진다(Repository에서 이 생성자가 사용됨)
        this.id = id;

        this.userId = userId;

        this.username = username;

        this.email = email;

        this.phoneNumber = phoneNumber;


    }

    public MemberDto(Member member) { // [페이징]을 사용할 때 사용하자!(Defaul batch size로 인해 지연로딩된 컬렉션을 그냥 바로 넣으면 된다.)

        this.id = member.getId();

        this.userId = member.getUserId();

        this.username = member.getUsername();

        this.email = member.getEmail();

        this.phoneNumber = member.getPhoneNumber();

        //컬렉션
        this.registers = member.getRegisters().stream()

                .map(register -> new RegisterDto(register))

                .collect(Collectors.toList());

    }


    public MemberDto(){

    }
}
