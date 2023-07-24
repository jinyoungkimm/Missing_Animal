package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.MissingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissingAddressRepositorySDJ extends JpaRepository<MissingAddress,Long> {


    // 쿼리 메서드 기능
    /*public long countMissingAddressBy();

    public MissingAddress findMissingAddressById(Long id);

    public List<MissingAddress> findMissingAddressByPrefecture(String prefecture);

    public List<MissingAddress> findMissingAddressByZipcode(String zipcode);

    public List<MissingAddress> findMissingAddressByCityName(String cityName);

    List<MissingAddress> findMissingAddressByGu(String gu);

    List<MissingAddress> findMissingAddressByStreetName(String streetName);*/

    /**
     * [쿼리 메서드 기능]은 파라미터가 증가하면, 메서드 이름이 매우 지저분해진다.
     * 따라서, @Query에다가 JPQL을 직접 작성하여 메서드의 이름이 길어지는 것을 예방하자!
     *
     */


    public long countMissingAddressBy();

    @Query("SELECT m FROM MissingAddress m WHERE m.id=:id")
    public MissingAddress findMissingAddressById(@Param("id") Long id);

    @Query("SELECT m FROM MissingAddress m WHERE m.prefecture=:prefecture")
    public List<MissingAddress> findMissingAddressByPrefecture(@Param("prefecture") String prefecture);

    @Query("SELECT m FROM MissingAddress m WHERE m.zipcode=:zipcode")
    public List<MissingAddress> findMissingAddressByZipcode(@Param("zipcode")String zipcode);

    @Query("SELECT m FROM MissingAddress m WHERE m.cityName=:cityName")
    public List<MissingAddress> findMissingAddressByCityName(@Param("cityName")String cityName);

    @Query("SELECT m FROM MissingAddress m WHERE m.gu =:gu")
    List<MissingAddress> findMissingAddressByGu(@Param("gu")String gu);

    @Query("SELECT m FROM MissingAddress m WHERE m.streetName=:streetName")
    List<MissingAddress> findMissingAddressByStreetName(@Param("streetName")String streetName);
}
