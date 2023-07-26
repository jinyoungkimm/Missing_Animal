package Portfolio.Missing_Animal;


import Portfolio.Missing_Animal.domainEntity.Hello;

import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.QHello;
import Portfolio.Missing_Animal.domainEntity.QMember;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        QHello qHello = QHello.hello; // new QHello() 과 같음(기본 인스턴스 라고 함)

       // 순수 JPA, Spring Data JPA와는 다르게 [순수 자바 코드]로 SQL문을 작성하고 있다.
       // 이 부분이 Querydsl의 가장 좋은 장점이다.
       // 왜냐하면 자바 코드를 사용하면, 컴파일 시점에 에러를 잡아 주기 때문이다.
       // (가장 좋은 에러는 [컴파일 에러], 가장 나쁜 에러는 [런타임 에러] )
        Hello result = query
               .selectFrom(qHello) // QClass를 projection!
               .fetchOne();

        assertThat(result).isEqualTo(hello); // 동일성

        assertThat(result.getId()).isEqualTo(hello.getId());

   }

    @Test
    void startQuerydsl1(){

        //when
        JPAQueryFactory query = new JPAQueryFactory(em);

        QMember qMember = QMember.member; // new QMember("member1")과 같음. ([기본 인스턴스]를 사용하여 별칭(member1)을 [간접적] 지정

        Member findMember = query

                .select(qMember) // [SELECT member1(별칭)]으로 빌드

                .from(qMember) // [FROM Member member1]으로 빌드

                .where( qMember.username.eq("김진영1") ) // [WHERE member1.username='김진영1']로 빌드.
                .fetchOne();

        //then
        assertThat(findMember.getUsername()).isEqualTo("김진영1");

   }

   @Test
    void startQuerydsl2(){

       //when
       JPAQueryFactory query = new JPAQueryFactory(em); // Querydsl은 EntityManager를 가지고 만든 JPAQueryFactory를 사용하여 쿼리문을 작성을 한다.
       QMember qMember = new QMember("A"); // [별칭]을 직접 지정

       // JPAQueryFactory를 가지고 쿼리문 작성.
       Member findMember = query

               .select(qMember) // [SELECT A]로 JPQL이 빌드된다.

               .from(qMember) // [FROM Member A]로 JPQL이 빌드된다.

               .where( qMember.username.eq("김진영1") )  // [WHERE A.username = '김진영1']로 JPQL이 빌드된다.
               // 파라미터 바인딩 처리(순수 JPQL에서는 SetParameter을 이용해서 [직접] 해줘야 했다)
               // Querydsl에서는 순수 자바 코드를 이용해서 [자동]으로 파라미터 바인딩을 해준다.
               .fetchOne();

       //then
       assertThat(findMember.getUsername()).isEqualTo("김진영1");


   }

    /**
     *  별칭 전략 : [기본 인스턴스]를 선택하자!
     */



    @Test
    void basicSearch1(){

        JPAQueryFactory query = new JPAQueryFactory(em);

        //when
        QMember qMember = QMember.member;

        Member member = query
                .select(qMember)
                .from(qMember)
                .where(qMember.username.eq("김진영1")

                        .and(qMember.userId.eq("wlsdud6523"))) // 여기서 WHERE 절이 끝남.

                .fetchOne();

        //then
        assertThat(member.getId()).isEqualTo(1L);


    }

    @Test
    void basicSearch2(){

        JPAQueryFactory query = new JPAQueryFactory(em);

        QMember qMember = QMember.member;

        List<Member> members
                = query

                .selectFrom(qMember)

                .where(qMember.username.eq("김진영1")

                .or(qMember.username.eq("김진영2")))

                .fetch();
        //then
        assertThat(members.get(0).getUsername()).isEqualTo("김진영1");
        assertThat(members.get(1).getUsername()).isEqualTo("김진영2");

    }








}
