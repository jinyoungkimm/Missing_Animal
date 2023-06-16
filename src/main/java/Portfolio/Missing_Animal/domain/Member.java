package Portfolio.Missing_Animal.domain;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.EmailForm;
import Portfolio.Missing_Animal.BirthDateForm;
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


}
