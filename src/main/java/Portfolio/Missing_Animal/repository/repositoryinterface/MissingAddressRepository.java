package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domain.MissingAddress;

import java.util.List;

public interface MissingAddressRepository {

    public Long save(MissingAddress missingAddress) ;

    public void delete(MissingAddress missingAddress);

    public long count();

    public MissingAddress findById(Long id)  ;

    // ex) prefecture : 충청남도, 전라남도...
    public List<MissingAddress> findByPrefecture(String prefecture)  ;

    public List<MissingAddress> findByZipcode(String zipcode)  ;


    public List<MissingAddress> findByCityName(String cityName)  ;

    public List<MissingAddress> findByGu(String gu);


    public List<MissingAddress> findByStreetName(String streetName);
}
