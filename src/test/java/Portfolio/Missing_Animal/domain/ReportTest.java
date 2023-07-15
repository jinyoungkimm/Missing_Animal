package Portfolio.Missing_Animal.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Transactional
class ReportTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    void a(){

        //givien
        Register register = new Register();
        register.setAnimalName("칸다");
        em.persist(register);


        Report report1 = new Report();
        report1.setRegister(register);
        report1.setFindedTime(LocalDateTime.now());
        em.persist(report1);

        Report report2 = new Report();
        report2.setRegister(register);
        report2.setFindedTime(LocalDateTime.now());
        em.persist(report2);


        //when


        //then


    }



}