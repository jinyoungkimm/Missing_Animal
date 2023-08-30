package Portfolio.Missing_Animal.service.serviceinterface;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegisterService {

    //실종 등록
    public Long registerMissing(Member member, Register register);

    //실종 목록
    public List<Register> listingRegister();

    public Page<Register> listingRegisterV2(Pageable pageable);

    public Register findOne(Long id);
    Long updateForm(Long registerId,Register register);

    public List<Register> ListingMissingAnimalByMissingAddress(MissingAddress missingAddress);

    Page<Register> searchByRegisterCond(RegisterSearchCond registerSearchCond, Pageable pageable);

    Page<Register> searchByRegisterCond2(RegisterSearchCond registerSearchCond, Pageable pageable);


    Page<Register> findRegiserInfo(String userId,Pageable pageable);
}
