package Portfolio.Missing_Animal.restAPI.APIService;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.SolvedIncidentDto;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.spring_data_jpa.MemberRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterRestApiServiceImpl implements RegisterRestApiService {

    //private final RegisterRepository registerRepository; // 순수 JPA Repository

    //private final MemberRepository memberRepository; // 순수 JPA Repository

    private final RegisterRepositorySDJ registerRepository; // Spring Data JPA

    private final MemberRepositorySDJ memberRepository; // Spring Data JPA

    @Override
    @Transactional
    public Long registerApi(Register register) {

        Member member = register.getMember(); // Context에 id값 존재 x
        String userId = member.getUserId();

        try {
            Member findMember = memberRepository.findByUserId(userId); // Context에 id값과 함꼐 존재!!
            register.setMember(findMember); // Member 교체!

            Long saveId = registerRepository.save(register).getId();


            return saveId;
        }
        catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 회원이 2개이상 조회가 되었습니다.");

        }

        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 회원이 조회되지 않습니다.");

        }

    }

    @Override
    @Transactional(readOnly = true)
    public SolvedIncidentDto countAllRegisters() {


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
