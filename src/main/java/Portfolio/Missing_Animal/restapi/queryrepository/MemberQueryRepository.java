package Portfolio.Missing_Animal.restapi.queryrepository;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.RegisterDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 *  컬렉션 최적화
 *  -> [1:다] 관계에서의 [다] 조회, 즉 컬렉션을 new 연산자로 [직접] DTO로 반환받기!!(내 포폴에서는 굳이 [직접] DTO로 받아가면서까지 컬렉션을 최적화시키지 X.
 *  Default batch fetch size 설정으로 컬렉션 최적화하는 게 더 적절하므로, 여기에서는 구현하지 x.JPA 활용 2의 P30참조)
 *  컬렉션 최적화는 [1:다] 관계에서 1에 해당하는 엔티티를 조회하는 Repository가 있고, 거기서 [다]에 해당하는 컬렉션을 조회할 필요가 있을 때 하는 최적화이다.
 *  고로, [다]에 해당하는 Register를 조회하는 Register(Query)Repository에서는 컬렉션 최적화라는 것이 성립이 안 된다.
 *  (root 쿼리 : 예를 들어,MemberRepository이면, Member를 조회하는 쿼리(ex.  SELECT m FROM Member m ) )
 *
 */


@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;

    public List<Member> findAllMembers() { // [페이징 불가능]

    return em.createQuery("SELECT m FROM Member m" +

            " JOIN FETCH m.registers r" // 컬렉션을 fetch join하게 되면 페이징 불가능

                    ,Member.class)
            .getResultList();
    }

    public List<Member> findMemberWithUserId(String userId) { // [페이징 불가능]

        return em.createQuery("SELECT m FROM Member m" +

                " JOIN FETCH m.registers r" +// 컬렉션을 페치 조인하게 되면, row수가 MissingAddress가 아닌, Register(컬렉션)을 기준으로 늘어 나므로, [페이징]도 Register을 기준으로 연산된다.
                                            // DB는 모든 컬렉션을 메모리로 들고와서 페이징을 실행을 하므로, 최악의 경우 장애로 연결이 된다.
                        /**
                         * Solution
                         * 1. toOne 관계만 일단 fetch join을 한다.
                         * 2. 컬렉션 조회는 @Batchsize or default-batch-fetch-size를 설정하여, [지연로딩]으로 [IN] 쿼리를 이용하여서 해당 컬렉션 데이터를 SIZE만큼 들고 온다.
                         * (참고로, 컬렉션은 단 1개만 FETCH JOIN이 된다. 2개 이상의 컬렉션에 대해서는 FETCH JOIN 못 함)
                         */

                        " WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    public List<Member> findAllMembers2() { // [페이징 불가능]

        return em.createQuery("SELECT m FROM Member m"

                                //" JOIN FETCH m.registers r" // 컬렉션을 fetch join하게 되면 페이징 불가능하므로, default batch fetch size로 [지연 로딩]을 통해

                        ,Member.class)
                .getResultList();
    }

    public List<Member> findMemberWithUserId2(String userId) { // [페이징 불가능]

        return em.createQuery("SELECT m FROM Member m" +

                       // " JOIN FETCH m.registers r" +


                        " WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getResultList();
    }



}
