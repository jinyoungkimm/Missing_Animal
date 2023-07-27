package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.repository.repositoryinterface.MissingAddressRepository;

import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissingAddressRepositoryImpl implements MissingAddressRepository {

    private final EntityManager em;

    private final MissingAddressRepositorySDJ missingAddressRepositorySDJ; // Spring Data JPA



    @Override
    public Long save(MissingAddress missingAddress){

        em.persist(missingAddress);

        Long saveId = missingAddress.getId();

        return saveId;
    }
    @Override
    public void delete(MissingAddress missingAddress){

        em.remove(missingAddress);

    }
    @Override
    public long count(){

        return em.createQuery("SELECT count(m) FROM MissingAddress m",Long.class)
                .getSingleResult();


    }

    @Override
    public MissingAddress findById(Long id){

        return em.createQuery("SELECT mr FROM MissingAddress mr" +
                " WHERE mr.id=:id",MissingAddress.class)
                .setParameter("id",id)
                .getSingleResult();

    }
    @Override
    // ex) prefecture : 충청남도, 전라남도...
    public List<MissingAddress> findByPrefecture(String prefecture){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                " WHERE ma.prefecture LIKE concat('%',:prefecture,'%') ",MissingAddress.class)
                .setParameter("prefecture",prefecture)
                .getResultList();

    }

    @Override
    public Page<MissingAddress> findByPrefectureWithPaging(String prefecture, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findByPrefecture(prefecture, pageRequest);

        return page;

    }

    @Override
    public List<MissingAddress> findByZipcode(String zipcode){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                " WHERE ma.zipcode LIKE concat('%',:zipcode,'%')",MissingAddress.class)
                .setParameter("zipcode",zipcode)
                .getResultList();
    }

    @Override
    public Page<MissingAddress> findByZipcodeWithPaging(String zipcode, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findByZipcode(zipcode, pageRequest);

        return page;

    }

    @Override
    public List<MissingAddress> findByCityName(String cityName){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                        " WHERE ma.cityName LIKE concat('%',:cityName,'%')"
                        ,MissingAddress.class)
                .setParameter("cityName",cityName)
                .getResultList();

    }

    @Override
    public Page<MissingAddress> findByCityNameWithPaging(String cityName, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findByCityName(cityName, pageRequest);

        return page;
    }


    @Override
    public List<MissingAddress> findByGu(String gu){

        return em.createQuery("SELECT ma FROM MissingAddress ma" +
                        " WHERE ma.gu LIKE concat('%',:gu,'%')"
                       ,MissingAddress.class)
                .setParameter("gu",gu)
                .getResultList();
    }

    @Override
    public Page<MissingAddress> findByGuWithPaging(String gu, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findByGu(gu, pageRequest);

        return page;



    }

    @Override
    public List<MissingAddress> findByDong(String Dong) {


        return em.createQuery("SELECT m FROM MissingAddress m" +

                " WHERE m.Dong LIKE concat('%',:Dong,'%')",MissingAddress.class)

                .setParameter("Dong",Dong)

                .getResultList();

    }

    @Override
    public Page<MissingAddress> findByDongWithPaging(String Dong, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findByDong(Dong, pageRequest);

        return page;

    }

    //[도로명] : 동적 쿼리를 사용하여 예를 들어, "헤운대로-28"처럼 도로명 번호가 있는 경우 [동적]으로 JPQL문을 바꿔줘야 한다.
    // -> 이 동적쿼리는 나중이 Querydsl을 사용해서 튜닝을 하자!

    @Override
    public List<MissingAddress> findByStreetName(String streetName){

    return em.createQuery("SELECT ma FROM MissingAddress ma" +
            " WHERE ma.streetName LIKE concat('%',:streetName,'%')",MissingAddress.class)
            .setParameter("streetName",streetName)
            .getResultList();

    }

    @Override
    public Page<MissingAddress> findByStreetNameWithPaging(String streetName, int pageNumber, int size) {

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findByStreetName(streetName, pageRequest);

        return page;

    }
}
