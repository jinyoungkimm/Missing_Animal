package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.enumType.ReportedStatus;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService { // 신고 관련 기능

    // 등록자 정보, 실종 등록 내역을 조회하기 위함!
    private final RegisterRepository registerRepository;

    private final ReportRepository reportRepository;

    // Spring Data JPA Repository
    private final RegisterRepositorySDJ registerRepositorySDJ;

    private final ReportRepositorySDJ reportRepositorySDJ;


    @Override
    @Transactional
    public Long saveReport(Long registerId, Report report) {

        //Register 조회
        Register register = registerRepositorySDJ.findById(registerId).get();
        register.setReportedStatus(ReportedStatus.YES);

        //Member 조회
        Member member = register.getMember();


        Report newReport = new Report();

        newReport.setRegister(register);
        newReport.setMember(member);

        newReport.setFindedTime(LocalDateTime.now());
        newReport.setFindedAddress(report.getFindedAddress());

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
