package Portfolio.Missing_Animal.domain.animal;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class AnimalTest {
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    void animal()
    {


        Register report = new Register();
        em.persist(report);

        Animal animal = new Dog();
        animal.setAge(10);
        animal.setName("사랑이");
        animal.setRegister(report);
        em.persist(animal);



        em.flush();
        em.clear();

    }



}