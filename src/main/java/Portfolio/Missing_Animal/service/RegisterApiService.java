package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.SolvedIncidentDto;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterApiService {

    private final RegisterRepository registerRepository;


    @Transactional(readOnly = true)
    public SolvedIncidentDto countAllRegisters(){

        List<Register> registers = registerRepository.findAll();

        if(registers.isEmpty())
            throw new IllegalStateException("실종 등록된 것이 1개도 없습니다.");

        int allRegistersCount=registers.size();
        int solvedRegistersCount=0;
        for (Register register : registers) {

            if(register.getRegisterStatus() == RegisterStatus.SOLVED)
               solvedRegistersCount++;

        }
        SolvedIncidentDto solvedIncidentDto = new SolvedIncidentDto();
        solvedIncidentDto.setAllRegistersCount(allRegistersCount);
        solvedIncidentDto.setSolvedRegistersCount(solvedRegistersCount);
        return solvedIncidentDto;
    }

}
