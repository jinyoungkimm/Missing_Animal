package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MissingAddressRepository {

    public Long save(MissingAddress missingAddress);

    public void delete(MissingAddress missingAddress);

    public long count();

    public MissingAddress findById(Long id);

    // ex) prefecture : 충청남도, 전라남도...
    public List<MissingAddress> findByPrefecture(String prefecture);

    public Page<MissingAddress> findByPrefectureWithPaging(String prefecture, int pageNumber, int size);


    public List<MissingAddress> findByZipcode(String zipcode);

    public Page<MissingAddress> findByZipcodeWithPaging(String prefecture, int pageNumber, int size);


    public List<MissingAddress> findByCityName(String cityName);

    public Page<MissingAddress> findByCityNameWithPaging(String cityName, int pageNumber, int size);


    public List<MissingAddress> findByGu(String gu);

    public Page<MissingAddress> findByGuWithPaging(String gu, int pageNumber, int size);


    public List<MissingAddress> findByDong(String Dong);

    public Page<MissingAddress> findByDongWithPaging(String Dong, int pageNumber, int size);

    public List<MissingAddress> findByStreetName(String streetName);

    public Page<MissingAddress> findByStreetNameWithPaging(String streetName, int pageNumber, int size);

}
