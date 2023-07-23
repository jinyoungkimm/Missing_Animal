package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.MissingAddressRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;

    private final MissingAddressRepository missingAddressRepository;

    //실종 등록
    @Override
    @Transactional
    public Long registerMissing(Register register) {

        Long saveId = registerRepository.save(register);

        return saveId;

    }

    //실종 목록
    @Override
    @Transactional(readOnly = true)
    public List<Register> listingRegister() {

        List<Register> all = registerRepository.findAll();

        return all;
    }

    @Override
    @Transactional(readOnly = true)
    public Register findOne(Long id) {

        Register register = registerRepository.findById(id);

        return register;
    }

    @Override
    @Transactional // dirty checking 이용
    public Long updateForm(Long registerId, Register register) {

        Register findRegister = registerRepository.findById(registerId);

        if(register.getFileName() != null)
            findRegister.setFileName(register.getFileName());

        if(register.getAnimalName() != null)
            findRegister.setAnimalName(register.getAnimalName());

        if(register.getAnimalSex() != null)
            findRegister.setAnimalSex(register.getAnimalSex());

        if(register.getAnimalVariety() != null)
            findRegister.setAnimalVariety(register.getAnimalVariety());

        if(register.getAnimalWeight()!= null)
            findRegister.setAnimalWeight(register.getAnimalWeight());

        if(register.getEtc() != null)
            findRegister.setEtc(register.getEtc());

        if(register.getRegisterStatus() != null)
            findRegister.setRegisterStatus(register.getRegisterStatus());

        if(register.getRegisterStatus() != null)
            findRegister.setReportedStatus(register.getReportedStatus());

        return registerId;
    }

    @Override
    @Transactional
    public List<Register> ListingMissingAnimalByMissingAddress(MissingAddress missingAddress){

        // [도시명]만 입력된 경우!
        // [도시명] + [구/군]이 입력된 경우
        // [도시명] + [구/군] + [동/읍/리]가 입력된 경우
        // [도시명] + [구/군] + [동/읍/리] + [도로명]이 입력된 경우!
        // -> 추후에 Querydsl로 MissingAddressRepository에서 동적 쿼리로 구현을 하자!!!

        // 지금은 [도시명]만으로 신고 기능을 만들자!
        if(missingAddress.getCityName() != null){

            List<MissingAddress> byCityName = missingAddressRepository.findByCityName(missingAddress.getCityName());

            if(byCityName.isEmpty() == true)
                throw new IllegalStateException("해당 도시에는 실종 등록된 동물이 없습니다.");

            List<Register> register = new ArrayList<>();

            byCityName.forEach(mr->{

                List<Register> registers = mr.getRegisters();

                for (Register register1 : registers) {

                    register.add(register1);
                }
            });

            return register;
        }


        return null;
    }


}
