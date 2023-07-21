package Portfolio.Missing_Animal.service.serviceinterface;


import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;

import java.util.List;

public interface RegisterService {

    //실종 등록
    public Long registerMissing(Register register);

    //실종 목록
    public List<Register> listingRegister();

    public Register findOne(Long id);
    void updateForm(Long id,String animalName);

    public List<Register> ListingMissingAnimalByMissingAddress(MissingAddress missingAddress);

}
