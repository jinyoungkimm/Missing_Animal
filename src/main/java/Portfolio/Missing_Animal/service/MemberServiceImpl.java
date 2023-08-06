package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;

import Portfolio.Missing_Animal.spring_data_jpa.MemberRepositorySDJ;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // 순수 JPA Repository

    private final MemberRepositorySDJ memberRepositorySDJ; // Spring Data JPA Repository
    private final PasswordEncoder bCryptPasswordEncoder; // passWordEncoder는 [인터페이스]이며, BCryptPasswordEncoder는 그 [구현체]이다.


    @Override
    @Transactional
    public Long join(Member member) { //회원가입

        //회원ID 중복 검사!
        isMemberExist(member);

        /**
         * Plain 비밀번호를 암호화 시켜서, 저장을 시킨다.
         */

        Member newMember = member.hashPassword(bCryptPasswordEncoder); // member 객체 안의 평문 비밀번호가 암호화된 비밀번호로 교체된다.

        Long saveId = memberRepositorySDJ.save(newMember).getId();

        return saveId;

    }


    @Override
    @Transactional(readOnly = true)
    public boolean login(Member member) { //로그인 기능(DB에 저장된 id값을 반환)


        try {
            /**
             * 먼저, 로그인 시 입력한 ID가, 회원가입이 된 ID인지를 검사해야 한다.( NoResultException, NonUniqueResultException 이용 )
             */
            Member findMember = memberRepositorySDJ.findByUserId(member.getUserId());


            /**
             *  이 시점에서, 해당 ID가 가입된 ID임은 증명이 됐다.
             *  다음으로는 db에 저장된 비밀번호와 로그인 시 입력한 비밀번호가 맞는지를 검사한다.
             */

            // 암호화된 비밀번호(findMember::password)와 평문 비밀번호(매개변수 member::password)를 비교한다.
            boolean isSame = findMember.checkPassword(member.getPassword(), bCryptPasswordEncoder);

            // 비교해서 맞으면, controller에 true를 넘기고, 아니면 throw를 날려 준다.
            if(isSame == true)
                return true;

            else
                throw new IllegalStateException("비밀번호가 틀렸습니다.");


        }
        catch(NonUniqueResultException e){ // 결과 값이 2개 이상일 떄!

            throw new IllegalStateException("결과값이 2개 이상 조회되었습니다.");

        }
        catch (NoResultException e){ // 결과값이 하나도 없을 떄!

            throw  new IllegalStateException("로그인 시 입력한 id가 존재하지 않습니다");
        }



    }

    @Override
    @Transactional(readOnly = true) // 회원 가입 시, id 중복 검사!
    public boolean isMemberExist(Member member) {

            try {

                Member findMember = memberRepositorySDJ.findByUserId(member.getUserId());

                //throw new IllegalStateException("중복되는 id가 1개 존재합니다.");
                if(findMember != null)
                    return false;
                else
                    return false;

            }
            catch(NonUniqueResultException e){ // 결과 값이 2개 이상일 떄!

                throw new IllegalStateException("결과값이 2개 이상 조회되었습니다.");

            }
            catch (NoResultException e){ // 결과값이 하나도 없을 떄!

                return false;
            }

    }

    @Override // 회원가입 당시 기입한 정보를 출력!
    @Transactional(readOnly = true)
    public Member memberInfo(String userId) {


        try {
            Member findMember = memberRepositorySDJ.findByUserId(userId);


            return findMember;
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("결과값이 2개 이상 조회됨");
        }
        catch(NoResultException e){

            throw  new IllegalStateException("해당 Member가 존재하지 않습니다.");
        }

    }



    @Override // 특정 회원이 등록한 실종 정보가 있다면 출력
    @Transactional(readOnly = true)
    public List<Register> findRegiserInfo(String userId) {

      /*  List<Member> findMember = memberRepository.findByUserId(userId);

        List<Register> registers = findMember.get(0).getRegisters();
        if(registers.isEmpty())
           throw new IllegalStateException("해당 회원이 등록한 실종 정보가 없습니다.");
        else
            return registers;*/

        try {
            Member findMember = memberRepositorySDJ.findByUserId(userId);

            List<Register> registers = findMember.getRegisters();

            if(registers.isEmpty())
                throw new IllegalStateException("해당 회원이 등록한 실종 정보가 없습니다.");

            else
                return registers;

        }
        catch(NonUniqueResultException e){

            throw new IllegalStateException("결과값이 2개 이상 조회됨");

        }
        catch(NoResultException e){

            throw  new IllegalStateException("해당 Member는 존재하지 않습니다.");
        }


    }



    @Override
    @Transactional(readOnly = true)
    public List<Report> findReportInfo(String userId) {

        try{

            Member findMember = memberRepositorySDJ.findByUserId(userId);
            List<Report> reports = findMember.getReports();

            if(reports.isEmpty())
                throw new IllegalStateException("해당 회원의 내역이 없습니다.");
            else
                return reports;
        }
        catch (NonUniqueResultException e){
            throw new IllegalStateException("해당 id의 회원이 2개 이상 조회되었습니다.");
        }
        catch(NoResultException e){
            throw new IllegalStateException("해당 id의 회원이 조회되지 않습니다.");
        }

    }



    @Override
    @Transactional(readOnly = true)
    public Member findOne(Long id) {


        Member findMember = memberRepositorySDJ.findById(id).get();


        return findMember;
    }

    @Override
    @Transactional // dirty checking을 이요하여 [수정]
    public Long updateMember(Long memberId, Member member) {

        Member findMember = memberRepository.findById(memberId); // dirty checking을 사용하기 위해서는 순수 JPA Repository를 사용해야 함!

        if(hasText(member.getUsername()))
            findMember.setUsername(member.getUsername());

        if(member.getEmail() != null)
            findMember.setEmail(member.getEmail());

        if(member.getBirthDate() != null)
            findMember.setBirthDate(member.getBirthDate());

        if(hasText(member.getPhoneNumber()))
            findMember.setPhoneNumber(member.getPhoneNumber());


        Long id = findMember.getId();

        return id;


    }


}
