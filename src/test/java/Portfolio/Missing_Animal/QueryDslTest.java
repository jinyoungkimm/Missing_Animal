package Portfolio.Missing_Animal;


import Portfolio.Missing_Animal.domainEntity.Hello;

import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.QHello;

import Portfolio.Missing_Animal.domainEntity.QMember;
import Portfolio.Missing_Animal.domainEntity.QRegister;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLOps;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static Portfolio.Missing_Animal.domainEntity.QMember.member;
import static Portfolio.Missing_Animal.domainEntity.QRegister.register;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory query;


    @BeforeEach
    void before(){

        query = new JPAQueryFactory(em);

    }

    @Test
    void contextLoads(){

        Hello hello = new Hello();
        em.persist(hello);


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


        QMember qMember = member; // new QMember("member1")과 같음. ([기본 인스턴스]를 사용하여 별칭(member1)을 [간접적] 지정

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
       //JPAQueryFactory query = new JPAQueryFactory(em); // Querydsl은 EntityManager를 가지고 만든 JPAQueryFactory를 사용하여 쿼리문을 작성을 한다.
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

        //when
        QMember qMember = member;

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



        QMember qMember = member;

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


    @Test
    void paging(){

        //QMember qMember = QMember.member; // import ~~~ QMember.*;을 설정해 주었기에, QMember에 선언된 static QMember member = new QMember("member1") 바로 사용 가능

        QueryResults<Member> results = query

                .selectFrom(member)

                .orderBy(member.username.desc())

                .offset(0L)

                .limit(2L)

                .fetchResults(); // fetch()와 달리, Pagination도 같이 반환


        assertThat(results.getTotal()).isEqualTo(4L);
        assertThat(results.getLimit()).isEqualTo(2L);
        assertThat(results.getOffset()).isEqualTo(0L);
        assertThat(results.getResults().size()).isEqualTo(2L);


    }

    @Test
    void testWithExtractingAndContainsExactly(){

        List<Member> fetch = query.selectFrom(member).fetch();

        // List<>에서 모든 "userId"를 뽑는다.
        assertThat(fetch).extracting("userId").containsExactly("wlsdud6523","wlsdud6524","wlsdud6525","wlsdud6526");


    }

    /**
     * 출력되는 SQL문을 보면 알겠지만, 모든 조인(INNER,OUTER JOIN등)은 FK(보통 id값)을 기반으로 조인 대상을 찾는다.
     *
     * ex)
     *          from
     *             register
     *         join
     *             member
     *                 on member_id = register.member_id // FK 값으로 조인 대상을 찾는다.
     *
     * Q. 근데 만약 on 절 뒤에 연관 관계 엔티티(여기서는 Member)에 대한 join condition이 없으면 어떻게 될까?
     * A. ON 절은 [조인 대상을 필터링]하기 위한 조건(Join Condition)이다.
     * FK값으로 연관 관계인 엔티티(Member)를 찾았다고 해도, Join Condition이 없으니, 해당 연관 관계 엔티티(Member)가 조인의 대상인지 아닌지를 모르므로,
     * 해당 엔티티는 조인 대상에서 필터링되버린다.
     * *** [조인 대상을 필터링] 할 떄에는 반드시 연관 관계에 있는 엔티티에 대한 join condition을 입력을 하자! ******
     */

    @Test
    void join_on_filtering(){

        List<Tuple> 사랑이 = query

                .select(register, member)

                .from(register) // Register이 join의 기준!

                .leftJoin(register.member, member) // Member : Register의 연관 관계 엔티티

                .on(member.username.eq("김진영1")) // On절 뒤를 [JOIN CONDITION]이라고도 부른다.즉 조인 대상을 필터링하고 있음.
                //.on(register.animalName.like("사랑이")) // Member에 대한 join condition이 없기에, Member는 null로 출력됨.
                .fetch();

        /**
         *  ON 절의 목적
         *  1. 조인 대상 필터링
         *  2. 외부 조인 시, join condition을 만족시키지는 연관 관계 엔티티가 없어도, null을 이용해서라도 table을 만들기 위함.
         * ( INNER JOIN일 때, join condition이 ON 절 뒤에 없으면 아무런 join table도 안 만들어 진다. 왜냐면, inner join은
         *  반드시 join condition을 만족시켜야지만 join table로 만들어 지기 때문! )
         */

        /**
         *  ON vs WHERE
         *  1. INNER JOIN인 경우 : ON 절에서 필터링하나 WHERE 절에서 필터링하나 결과가 똑같으므로, 익숙한 WHERE절을 사용하자!
         *  2. OUUTER JOIN인 경우 : ON 절 사용하자!!!
         */

        for (Tuple tuple : 사랑이) {

            System.out.println("tuple = " + tuple);

        }

    }


    @Test
    void test(){

        String result = query
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})", member.username, "김진영", "M"))
                .from(member)
                .fetchFirst();

        System.out.println("result = " + result);

    }

}
