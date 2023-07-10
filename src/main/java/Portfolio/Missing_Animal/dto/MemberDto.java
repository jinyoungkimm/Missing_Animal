package Portfolio.Missing_Animal.dto;


import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.domain.Member;
import lombok.Data;

@Data
public class MemberDto {
    Long id;
    String userId;
    String username;
    EmailForm email;
    String phoneNumber;
    Boolean isRegister;

    public MemberDto(Member member) {

        this.id = member.getId();

        this.userId = member.getUserId();

        this.username = member.getUsername();

        this.email = member.getEmail();

        this.phoneNumber = member.getPhoneNumber();

        if(member.getRegisters().isEmpty()) // Lazy Loading 발생(고로, fetch join으로 조회)
            this.isRegister = false;
        else
            this.isRegister = true;

    }

    public MemberDto(){

    }
}
