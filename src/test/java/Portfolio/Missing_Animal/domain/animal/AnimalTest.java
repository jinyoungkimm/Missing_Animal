package Portfolio.Missing_Animal.domain.animal;

import Portfolio.Missing_Animal.domain.Member;
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

        Dog dog = new Dog();
        em.persist(dog);
        Long id = dog.getId();

        em.flush();
        em.clear();

        Animal anumal = em.createQuery("select a from Animal a where a.id =:id", Animal.class)
                .setParameter("id", id)
                .getSingleResult();

        Dog d = (Dog)anumal;

        System.out.println(anumal.getId());





    }
}