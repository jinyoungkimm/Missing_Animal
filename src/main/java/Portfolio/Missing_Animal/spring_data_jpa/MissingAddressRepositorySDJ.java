package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<MissingAddress> findAll(Pageable pageable);
    @Query("SELECT m FROM MissingAddress m WHERE m.prefecture LIKE concat('%',:prefecture,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<MissingAddress> findByPrefecture(@Param("prefecture") String prefecture,Pageable pageable);
    @Query("SELECT m FROM MissingAddress m WHERE m.zipcode LIKE concat('%',:zipcode,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<MissingAddress> findByZipcode(@Param("zipcode")String zipcode,Pageable pageable);
    @Query("SELECT m FROM MissingAddress m WHERE m.cityName  LIKE concat('%',:cityName,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<MissingAddress> findByCityName(@Param("cityName")String cityName,Pageable pageable);
    @Query("SELECT m FROM MissingAddress m WHERE m.gu  LIKE concat('%',:gu,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<MissingAddress> findByGu(@Param("gu")String gu,Pageable pageable);

    @Query("SELECT m FROM MissingAddress m WHERE m.Dong  LIKE concat('%',:Dong,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    Page<MissingAddress> findByDong(@Param("Dong")String Dong,Pageable pageable);
    @Query("SELECT m FROM MissingAddress m WHERE m.streetName LIKE concat('%',:streetName,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Page<MissingAddress> findByStreetName(@Param("streetName")String streetName,Pageable pageable);


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query("SELECT m FROM MissingAddress m WHERE m.id=:id")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public Optional<MissingAddress> findById(@Param("id") Long id);

    @Query("SELECT m FROM MissingAddress m WHERE m.prefecture LIKE concat('%',:prefecture,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public List<MissingAddress> findByPrefecture(@Param("prefecture") String prefecture);

    @Query("SELECT m FROM MissingAddress m WHERE m.zipcode LIKE concat('%',:zipcode,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public List<MissingAddress> findByZipcode(@Param("zipcode")String zipcode);

    @Query("SELECT m FROM MissingAddress m WHERE m.cityName  LIKE concat('%',:cityName,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    public List<MissingAddress> findByCityName(@Param("cityName")String cityName);

    @Query("SELECT m FROM MissingAddress m WHERE m.gu  LIKE concat('%',:gu,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    List<MissingAddress> findByGu(@Param("gu")String gu);

    @Query("SELECT m FROM MissingAddress m WHERE m.Dong  LIKE concat('%',:Dong,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    List<MissingAddress> findByDong(@Param("Dong")String Dong);

    @Query("SELECT m FROM MissingAddress m WHERE m.streetName LIKE concat('%',:streetName,'%')")
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    List<MissingAddress> findByStreetName(@Param("streetName")String streetName);
}
