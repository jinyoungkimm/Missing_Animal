package Portfolio.Missing_Animal.QueryrestApi.queryrepository;

import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.ReportDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MemberQueryRepositoryTest {

    @Autowired
    MemberQueryRepository memberQueryRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void findAllMembers() {

        List<Member> allMembers = memberQueryRepository.findAllMembers();

       allMembers.get(0).getRegisters().get(0).getAnimalName(); // fetch join을 사용하였기에 (1+N) 문제 해결
       allMembers.get(0).getRegisters().get(1).getAnimalName(); // fetch join을 사용하였기에 (1+N) 문제 해결

    }

    @Test
    @Transactional
    void findMembers3(){

        List<MemberDto> allMembers3 = memberQueryRepository.findAllMembers3();

    }

    @Test
    @Transactional
    void findMembers4(){

        List<MemberDto> allMembers3 = memberQueryRepository.findAllMembers4();

        assertThat(allMembers3.size()).isEqualTo(4);

        for (MemberDto memberDto : allMembers3) {

            System.out.println(memberDto.getRegisters().get(0).getAnimalName());
            System.out.println(memberDto.getRegisters().get(1).getAnimalName());



        }

    }


    @Test
    void findMemberWithUserId() {

        //givien
        String userId = "wlsdud6523";

        //when
        Member member = memberQueryRepository.findMemberWithOneUserId(userId);

        //then
        // fetch join을 사용하였기에 (1+N) 문제 해결
        assertThat(member.getRegisters().get(0).getAnimalName()).isEqualTo("사랑이1");
        assertThat(member.getRegisters().get(1).getAnimalName()).isEqualTo("사랑이2");



    }

    @Test
    void findMemberWithUserId2() {

        //givien
        String userId = "wlsdud6523";

        //when
        MemberDto member = memberQueryRepository.findMemberWithOneUserId2(userId);

        //then
        // fetch join을 사용하였기에 (1+N) 문제 해결
        assertThat(member.getRegisters().get(0).getAnimalName()).isEqualTo("사랑이1");
        assertThat(member.getRegisters().get(1).getAnimalName()).isEqualTo("사랑이2");

        assertThat(member.getReports().get(0).getReportId()).isEqualTo(1);
        assertThat(member.getReports().get(1).getReportId()).isEqualTo(2);
        assertThat(member.getReports().get(2).getReportId()).isEqualTo(3);
        assertThat(member.getReports().get(3).getReportId()).isEqualTo(4);

    }

    @Test
    void findedAddress(){

        List<MemberDto> allMembers = memberQueryRepository.findAllMembers4();

        for (MemberDto allMember : allMembers) {

            List<ReportDto> reports = allMember.getReports();

            for (ReportDto report : reports) {
                System.out.println(report.getFindedAddress());
            }

        }


    }

}