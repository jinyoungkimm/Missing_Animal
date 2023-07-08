package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;

    //실종 등록
    @Override
    @Transactional
    public void registerMissing(Register register) {

        registerRepository.save(register);

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

        Register register = registerRepository.findByAnimalId(id);

        return register;
    }

    @Override
    @Transactional // dirty checking 이용
    public void updateForm(Long id, String animalName) {

        Register findRegister = registerRepository.findByAnimalId(id);
        findRegister.setAnimalName(animalName);

    }


}
