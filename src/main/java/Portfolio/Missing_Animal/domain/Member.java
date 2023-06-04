package Portfolio.Missing_Animal.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @OneToMany(mappedBy = "member")
    List<Register> registers = new ArrayList<>();


}
