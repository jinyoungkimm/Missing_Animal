package Portfolio.Missing_Animal.domain;


import jakarta.persistence.*;

@Entity
@Table(name="demo_table")
public class Demo {

    @Id@GeneratedValue
    @Column(name="demo_id")
    private Long id;

    private String demo;


}
