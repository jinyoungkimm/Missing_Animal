package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.repository.repositoryinterface.MissingAddressRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissingAddressRepositoryImpl implements MissingAddressRepository {

    private final EntityManager em;


    public Long save(MissingAddress missingAddress){

        em.persist(missingAddress);

        Long saveId = missingAddress.getId();

        return saveId;
    }

    public void delete(MissingAddress missingAddress){

        em.remove(missingAddress);

    }

    public long count(){

        return em.createQuery("SELECT count(m) FROM MissingAddress m",Long.class)
                .getSingleResult();


    }


    public MissingAddress findById(Long id){

        return em.createQuery("SELECT mr FROM MissingAddress mr" +
                " WHERE mr.id=:id",MissingAddress.class)
                .setParameter("id",id)
                .getSingleResult();

    }

    // ex) prefecture : 충청남도, 전라남도...
    public List<MissingAddress> findByPrefecture(String prefecture){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                " WHERE ma.prefecture LIKE concat('%',:prefecture,'%') ",MissingAddress.class)
                .setParameter("prefecture",prefecture)
                .getResultList();

    }

    public List<MissingAddress> findByZipcode(String zipcode){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                " WHERE ma.zipcode=:zipcode",MissingAddress.class)
                .setParameter("zipcode",zipcode)
                .getResultList();
    }

    //[시] ex) 부산광역[시], 합천[군]
    // LIKE 키워드로 [~시/군]을 조회
    // substr() 함수와 in 키워드를 사용하여 [특정 위치]에 여러개의 문자가 해당하는 경우를 추출하여 조회도 가능
    // LIKE IN 키워드로도 조회 가능!
    // https://jhnyang.tistory.com/entry/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-SQL-LIKE-%EC%82%AC%EC%9A%A9%EB%B2%95-%ED%8A%B9%EC%A0%95-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%B4-%ED%8F%AC%ED%95%A8%EB%90%98%EC%96%B4-%EC%9E%88%EB%8A%94%EC%A7%80-%EA%B2%80%EC%83%89%ED%95%98%EA%B8%B0-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%B6%80%EB%B6%84%EC%9D%BC%EC%B9%98-%EC%BB%AC%EB%9F%BC-%EC%A1%B0%ED%9A%8C%ED%95%98%EA%B8%B0
    // 위 사이트 참조!
    public List<MissingAddress> findByCityName(String cityName){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                        " WHERE ma.cityName LIKE concat('%',:cityName,'%')"
                        ,MissingAddress.class)
                .setParameter("cityName",cityName)
                .getResultList();

    }
    //[군/구]
    // LIKE 키워드로 [~구/군]을 조회
    // substr() 함수와 in 키워드를 사용하여 [특정 위치]에 여러개의 문자가 해당하는 경우를 추출하여 조회도 가능
    public List<MissingAddress> findByGu(String gu){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                        " WHERE ma.gu LIKE concat('%',:gu,'%')"
                       ,MissingAddress.class)
                .setParameter("gu",gu)
                .getResultList();
    }

    //[도로명] : 동적 쿼리를 사용하여 예를 들어, "헤운대로-28"처럼 도로명 번호가 있는 경우 [동적]으로 JPQL문을 바꿔줘야 한다.
    // -> 이 동적쿼리는 나중이 Querydsl을 사용해서 튜닝을 하자!
    public List<MissingAddress> findByStreetName(String streetName){

    return em.createQuery("SELECT ma FROM MissingAddress ma" +
            " WHERE ma.streetName LIKE concat('%',:streetName,'%')",MissingAddress.class)
            .setParameter("streetName",streetName)
            .getResultList();

    }

    //       // 도시명만 입력된 경우!
    //
    //        // [도시명] + [구/군]이 입력된 경우
    //
    //        // [도시명] + [구/군] + [동/읍/리]가 입력된 경우
    //
    //        // [도시명] + [구/군] + [동/읍/리] + [도로명]이 입력된 경우!
    // -> 위 경우를 추후에 Querydsl을 사용하여 동적 쿼리문으로 만든다.


}
