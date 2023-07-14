package Portfolio.Missing_Animal.restapi.queryrepository;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@RequiredArgsConstructor
public class MissingAddressQueryRepository {

    private final EntityManager em;

    public List<MissingAddress> findAllMissingAddress(){ // [페이징] 불가능!

        return  em.createQuery("SELECT mr FROM MissingAddress mr" +

                                " JOIN FETCH mr.registers r"// 컬렉션을 페치 조인하게 되면, row수가 MissingAddress가 아닌, Register(컬렉션)을 기준으로 늘어 나므로, [페이징]도 Register을 기준으로 연산된다.
                                                            // DB는 모든 컬렉션을 메모리로 들고와서 페이징을 실행을 하므로, 최악의 경우 장애로 연결이 된다.

                        /**
                         * Solution
                         * 1. toOne 관계만 일단 fetch join을 한다.
                         * 2. 컬렉션 조회는 @Batchsize or default-batch-fetch-size를 설정하여, [IN] 쿼리를 이용하여서 해당 컬렉션 데이터를 SIZE만큼 들고 온다.
                         */
                        ,MissingAddress.class)
                .getResultList();

    }

    public List<MissingAddress> findAllMissingAddress2(int offset,int limit){ // [페이징 가능]

        return  em.createQuery("SELECT mr FROM MissingAddress mr"

                               // " JOIN FETCH mr.registers r"

                        ,MissingAddress.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    public List<MissingAddress> findById(Long id){ // [페이징] 불가능

        return em.createQuery("SELECT mr FROM MissingAddress mr" +

                " JOIN FETCH mr.registers r"+

                " WHERE mr.id=:id",MissingAddress.class)
                .setParameter("id",id)
                .getResultList();
    }


    //여기서부터 아래는 Querydsl로 구현을 할 것이다.

    /**
     * 2. api/missingaddress/{prefecture }
     * 3. api/missingaddress/{prefecture }/{cityName}
     * 4. api/missingaddress{prefecture }/{cityName}/{gu}
     * 5. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}
     * 6. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}/{streetNumber}
     * @param prefecture
     * @return
     */

    public List<MissingAddress> findRegisterWithPrefecture(String prefecture){

        return em.createQuery("SELECT mr FROM MissingAddress mr" +
                        " JOIN FETCH mr.registers r" +
                        " WHERE mr.prefecture=:prefecture ", MissingAddress.class)
                .setParameter("prefecture",prefecture)
                .getResultList();

    }

    public List<MissingAddress> findRegisterWithCityOrStreet(String cityName){

        return em.createQuery("SELECT mr FROM MissingAddress mr" +
                        " JOIN FETCH r.member m" +
                        " WHERE mr.cityName=:cityName ",MissingAddress.class)
                .setParameter("cityName",cityName)
                .getResultList();

    }


}
