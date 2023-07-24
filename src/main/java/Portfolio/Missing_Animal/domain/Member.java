package Portfolio.Missing_Animal.domain;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.BirthDateForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
@Getter@Setter
@ToString(of={"id","username","userId","address","email","birthDate","phoneNumber"})
public class Member {

    @Id@GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;

    //회원 ID(ex. wlsdud6523)
    private String userId;

    private String password;

    @Embedded
    private AddressForm address;

    @Embedded
    private EmailForm email;

    @Embedded
    private BirthDateForm birthDate;

    private String phoneNumber;


    @OneToMany(mappedBy = "member")
    private List<Register> registers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Report> reports = new ArrayList<>();

    public Member() {

    }


    /**
     * 비밀번호를 암호화
     * @param passwordEncoder 암호화 할 인코더 클래스
     * @return 변경된 유저 Entity
     */
    public Member hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    /**
     * 비밀번호 확인
     * @param plainPassword 암호화 이전의 비밀번호
     * @param passwordEncoder 암호화에 사용된 클래스
     * @return true | false
     */
    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }

}
