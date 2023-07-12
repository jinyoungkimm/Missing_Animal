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

    public List<MissingAddress> findAllMissingAddress(){

        return  em.createQuery("SELECT mr FROM MissingAddress mr",MissingAddress.class)
                .getResultList();

    }

    public List<MissingAddress> findById(Long id){

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
