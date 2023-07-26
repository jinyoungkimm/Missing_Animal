package Portfolio.Missing_Animal;


import Portfolio.Missing_Animal.domain.Hello;
import Portfolio.Missing_Animal.domain.Member;

import Portfolio.Missing_Animal.domain.QHello;
import Portfolio.Missing_Animal.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @Autowired
    EntityManager em;

   @Test
    void contextLoads(){

        Hello hello = new Hello();
        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QHello qHello = QHello.hello; // new QHello() 과 같음

        Hello result = query
               .selectFrom(qHello)
               .fetchOne();

        assertThat(result).isEqualTo(hello); // 동일성

        assertThat(result.getId()).isEqualTo(hello.getId());

   }


}
