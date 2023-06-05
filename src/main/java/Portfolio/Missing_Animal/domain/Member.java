package Portfolio.Missing_Animal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id@GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;


    @OneToMany(mappedBy = "member")
    List<Register> registers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Report> reports = new ArrayList<>();


}
