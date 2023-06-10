package Portfolio.Missing_Animal.domain;

import Portfolio.Missing_Animal.domain.animal.Animal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Report { // 발견 신고(Member와 Animal의 중간 Table 역할)

    @Id
    @GeneratedValue
    @Column(name="suspicious_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    Animal animal;

    private LocalDateTime findedTime;

}
