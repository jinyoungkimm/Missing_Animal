package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.MissingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissingAddressRepositorySDJ extends JpaRepository<MissingAddress,Long> {


    // 쿼리 메서드 기능
    public long countMissingAddressBy();

    public MissingAddress findMissingAddressById(Long id);

    public List<MissingAddress> findMissingAddressByPrefecture(String prefecture);

    public List<MissingAddress> findMissingAddressByZipcode(String zipcode);

    public List<MissingAddress> findMissingAddressByCityName(String cityName);

    List<MissingAddress> findMissingAddressByGu(String gu);

    List<MissingAddress> findMissingAddressByStreetName(String streetName);
}
