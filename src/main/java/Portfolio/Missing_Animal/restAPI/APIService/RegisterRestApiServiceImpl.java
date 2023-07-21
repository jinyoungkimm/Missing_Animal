package Portfolio.Missing_Animal.restAPI.APIService;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterRestApiServiceImpl implements RegisterRestApiService {

    private final RegisterRepository registerRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long registerApi(Register register) {

        Member member = register.getMember(); // Context에 id값 존재 x
        String userId = member.getUserId();

        try {
            Member findMember = memberRepository.findByUserId(userId); // Context에 id값과 함꼐 존재!!
            register.setMember(findMember); // Member 교체!

            Long saveId = registerRepository.save(register);


            return saveId;
        }
        catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 회원이 2개이상 조회가 되었습니다.");

        }

        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 회원이 조회되지 않습니다.");

        }

    }
}
