package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;

import Portfolio.Missing_Animal.spring_data_jpa.MemberRepositorySDJ;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;


@Service
@RequiredArgsConstructor
@Slf4j
@LogTrace
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // 순수 JPA Repository [인터페이스]
    private final MemberRepositorySDJ memberRepositorySDJ; // Spring Data JPA Repository [인터페이스]
    private final PasswordEncoder bCryptPasswordEncoder; // passWordEncoder는 [인터페이스]이며, , BCryptPasswordEncoder는 그 [구현체]이다.


    @Override
    @Transactional
    public Long join(Member member) { //회원가입

        //회원ID 중복 검사!
        boolean memberExist = isMemberExist(member);

        if(memberExist == false)
        {
            log.info("이미 사용 중인 ID입니다.");
            return null;

        }

        /**
         * Plain 비밀번호를 암호화 시켜서, 저장을 시킨다.
         */
        Member newMember = member.hashPassword(bCryptPasswordEncoder); // member 객체 안의 평문 비밀번호가 암호화된 비밀번호로 교체된다.

        Long saveId = memberRepositorySDJ.save(newMember).getId();

        return saveId;

    }


    @Override
    @Transactional(readOnly = true)
    public Member login(Member member) { //로그인 기능(DB에 저장된 id값을 반환)

            /**
             * 먼저, 로그인 시 입력한 ID가, 회원가입이 된 ID인지를 검사해야 한다.( NoResultException, NonUniqueResultException 이용 )
             */
            Member findMember = memberRepositorySDJ.findByUserId(member.getUserId());

            /**
             *  이 시점에서, 해당 ID가 가입된 ID임은 증명이 됐다.
             *  다음으로는 db에 저장된 비밀번호와 로그인 시 입력한 비밀번호가 맞는지를 검사한다.
             */

            // 암호화된 비밀번호(findMember::password)와 평문 비밀번호(매개변수 member::password)를 비교한다.
             if(findMember != null) {
                 boolean isSame = findMember.checkPassword(member.getPassword(), bCryptPasswordEncoder);

                 // 비교해서 맞으면, controller에 true를 넘기고, 아니면 throw를 날려 준다.
                 if (isSame == true)
                     return findMember;

                 else {
                     log.info("비밀번호가 틀렸습니다.");
                     return null;

                 }
             }

             else
                 return null;

    }

    @Override
    @Transactional(readOnly = true) // 회원 가입 시, id 중복 검사!
    public boolean isMemberExist(Member member) {

                Member findMember = memberRepositorySDJ.findByUserId(member.getUserId());

                if(findMember != null) // 이미 사용 중인 회원 ID가 있는 경우!
                    return false;

                else
                    return true;

    }

    @Override // 회원가입 당시 기입한 정보를 출력!
    @Transactional(readOnly = true)
    public Member memberInfo(String userId) {


            Member findMember = memberRepository.findByUserId(userId);

            return findMember;

    }


    @Override // 특정 회원이 등록한 실종 정보가 있다면 출력
    @Transactional(readOnly = true)
    public List<Register> findRegiserInfo(String userId) {


            Member findMember = memberRepositorySDJ.findByUserId(userId);

            List<Register> registers = findMember.getRegisters();

            if(registers.isEmpty())
                throw new IllegalStateException("해당 회원이 등록한 실종 정보가 없습니다.");

            else
                return registers;



    }


    @Override
    @Transactional(readOnly = true)
    public List<Report> findReportInfo(String userId) {

            Member findMember = memberRepositorySDJ.findByUserId(userId);
            List<Report> reports = findMember.getReports();

            if(reports.isEmpty())
                throw new IllegalStateException("해당 회원의 내역이 없습니다.");
            else
                return reports;

    }


    @Override
    @Transactional(readOnly = true)
    public Member findOne(Long id) {


        Member findMember = memberRepositorySDJ.findById(id).get();


        return findMember;
    }

    @Override
    @Transactional // dirty checking을 이용하여 [수정]
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
