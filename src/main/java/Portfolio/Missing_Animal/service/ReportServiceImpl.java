package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import Portfolio.Missing_Animal.repository.MissingAddressRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import Portfolio.Missing_Animal.service.serviceinterface.ReportService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService { // 신고 관련 기능

    // 등록자 정보, 실종 등록 내역을 조회하기 위함!
    private final RegisterRepository registerRepository;

    // 실종동물을 [주소]로 검색하여서 조회하고 싶을 때!
    private final MissingAddressRepository missingAddressRepository;

    private final MemberRepository memberRepository;

    private final ReportRepository reportRepository;


    @Override
    @Transactional
    public Long saveReport(Long registerId, AddressForm findedAddress) {

        //Register 조회
        Register register = registerRepository.findById(registerId);
        register.setReportedStatus(ReportedStatus.YES);

        //Member 조회
        Member member = register.getMember();


        Report report = new Report();
        report.setRegister(register);
        report.setMember(member);
        report.setFindedTime(LocalDateTime.now());
        report.setFindedAddress(findedAddress);

        //report 저장
        Long saveId = reportRepository.save(report);

        return saveId;
    }


    @Override
    @Transactional(readOnly = true)
    public Report findOne(Long reportId){

        try {
            Report report = reportRepository.findById(reportId);

            return report;
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("해당 id의 Report가 2개이상 조회됨");
        }
        catch (NoResultException e){
            throw new IllegalStateException("해당 id의 Report가 조회되지 않음");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findAllReports() {

        List<Report> all = reportRepository.findAll();

        return all;

    }

    @Override
    @Transactional // dirty checking 이용
    public void updateReport(Long reportId, Report report) {

        Report findreport = reportRepository.findById(reportId);

        //dirty checking!
        findreport.setFindedTime(report.getFindedTime());
        findreport.setFindedAddress(report.getFindedAddress());
        findreport.setAnimal(report.getAnimal());



    }
}
