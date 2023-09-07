package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.enumType.ReportedStatus;

import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import Portfolio.Missing_Animal.spring_data_jpa.MemberRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.ReportRepositorySDJ;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@LogTrace
@Slf4j
public class ReportServiceImpl implements ReportService { // 신고 관련 기능

    // 등록자 정보, 실종 등록 내역을 조회하기 위함!
    private final RegisterRepository registerRepository;

    private final MemberRepository memberRepository;

    private final ReportRepository reportRepository;

    // Spring Data JPA Repository
    private final RegisterRepositorySDJ registerRepositorySDJ;

    private final ReportRepositorySDJ reportRepositorySDJ;


    @Override
    @Transactional
    public Long saveReport(Member member,Long registerId, Report report) {

        Report newReport = new Report();

        //Register 조회
        Register register = registerRepositorySDJ.findById(registerId).get();
        register.setReportedStatus(ReportedStatus.YES);
        newReport.setRegister(register);

        /**
         * 로그인 시에 입력하는 회원Id(Member:userId)는 이미 DB에 저장된 데이터이다.(회원 가입 시에 이미 회원 ID가 DB에 저장돼 있다.)
         * -> 고로, 로그인 시, SESSION Table에 {sessionId,Member}에서의 Member는 이미 DB ID값을 가진 상태이다.
         * 고로, memberRepository.findByUserId(member.getUserId())로 Member를 조회하지 않고, newRepot.setMember(member)를 해버리게 되면
         * 이미 DB 엔티티에 @GeneratedValue를 사용해서 자동생성하겠다고 선언을 했는데
         * id가 세팅된 상태에서 persist를 호출하면 JPA는 해당 객체가 detached 상태의 객체라 생각한다.
         * detached 객체는 이전에 한번 영속화 되었던 적이 있는 객체 (DB에 저장되어 있는 데이터를 JPA의 findById로 조회한 엔티티)
         * 즉, 이 member 객체를 소위 [준영속] 상태로 인식을 하기에, Merge를 사용해서 해결해야 한다.
         * 그러나 Merge의 사용은 되도록 자제해야 한다.
         */
        Member findMember = memberRepository.findByUserId(member.getUserId());
        newReport.setMember(findMember);


        newReport.setFindedTime(LocalDateTime.now());
        newReport.setFindedAddress(report.getFindedAddress());
        newReport.setFileName(report.getFileName());

        //report 저장
        Long saveId = reportRepositorySDJ.save(newReport).getId();

        return saveId;
    }


    @Override
    @Transactional(readOnly = true)
    public Report findOne(Long reportId){

       // try {
            Report report = reportRepositorySDJ.findById(reportId).get();

            return report;
     //   }
      /*  catch(NonUniqueResultException e){
            throw new IllegalStateException("해당 id의 Report가 2개이상 조회됨");
        }
        catch (NoResultException e){
            throw new IllegalStateException("해당 id의 Report가 조회되지 않음");
        }*/
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findAllReports() {

        List<Report> all = reportRepositorySDJ.findAll();

        return all;

    }

    @Override
    @Transactional // dirty checking 이용
    public Long updateReport(Long reportId, Report report) {

        Report findreport = reportRepository.findById(reportId); // dirty checking을 사용하기 위해서는 순수 JPA Repository를 사용해줘야 한다.

        //dirty checking!
        findreport.setFileName(report.getFileName());
        findreport.setFindedTime(report.getFindedTime());
        findreport.setFindedAddress(report.getFindedAddress());
        findreport.setAnimal(report.getAnimal());

        return reportId;
    }

    @Override
    public Page<Report> findReportInfo(String userId, Pageable pageable) {

        Page<Report> reports = reportRepository.findByUserId(userId, pageable);

        return reports;

    }
}
