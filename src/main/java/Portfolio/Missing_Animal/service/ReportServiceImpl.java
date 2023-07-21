package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.Member;
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

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService { // 신고 관련 기능

    // 등록자 정보, 실종 등록 내역을 조회하기 위함!
    private final RegisterRepository registerRepository;

    // 실종동물을 [주소]로 검색하여서 조회하고 싶을 때!
    private final MissingAddressRepository missingAddressRepository;

    private final MemberRepository memberRepository;

    private final ReportRepository reportRepository;

    // 실종 리스트 페이지에 있는 실종 등록된 리스트를 클릭을 하면 id값을 전달받아,즉 Register 엔티티의 id을 전달받아 실종 등록 내역을 조회한다.
    @Transactional
    public Report showingRegisterContentById(Long registerId,String userId) {

        Register register = registerRepository.findById(registerId);

        //신고됨을 알림
        register.setReportedStatus(ReportedStatus.YES);

        //신고자 정보
        //추후에 cookie에 id를 설정하여서, 서버에서 받을 것이다.
        try {
            Member finder = memberRepository.findByUserId("wlsdud6523");

            Report report = new Report();
            report.setRegister(register);
            report.setMember(finder);
            report.setFindedTime(LocalDateTime.now());

            reportRepository.save(report);

            return report;
        } catch (NonUniqueResultException e) {

            throw new IllegalStateException("해당 id의 회원이 2개 이상 조회가 되었습니다.");

        } catch (NoResultException e) {
            throw new IllegalStateException("해당 id의 회원이 조회되지 않습니다.");
        }

    }
}
