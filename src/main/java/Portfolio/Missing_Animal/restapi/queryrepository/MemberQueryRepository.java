package Portfolio.Missing_Animal.restapi.queryrepository;


import Portfolio.Missing_Animal.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;

    public List<Member> findAllMembers() {

    return em.createQuery("SELECT m FROM Member m" +
            " JOIN FETCH m.registers r" ,Member.class)
            .getResultList();
    }

    public List<Member> findMemberWithUserId(String userId) {

        return em.createQuery("SELECT m FROM Member m" +

                " JOIN FETCH m.registers r" +// 컬렉션을 페치 조인하게 되면, row수가 MissingAddress가 아닌, Register(컬렉션)을 기준으로 늘어 나므로, [페이징]도 Register을 기준으로 연산된다.
                                            // DB는 모든 컬렉션을 메모리로 들고와서 페이징을 실행을 하므로, 최악의 경우 장애로 연결이 된다.
                        /**
                         * Solution
                         * 1. toOne 관계만 일단 fetch join을 한다.
                         * 2. 컬렉션 조회는 @Batchsize or default-batch-fetch-size를 설정하여, [IN] 쿼리를 이용하여서 해당 컬렉션 데이터를 SIZE만큼 들고 온다.
                         * (참고로, 컬렉션은 단 1개만 FETCH JOIN이 된다. 2개 이상의 컬렉션에 대해서는 FETCH JOIN 못 함)
                         */

                        " WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getResultList();
    }


}
