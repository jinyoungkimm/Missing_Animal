package Portfolio.Missing_Animal.exception.QueryrestApi.queryrepository;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.dto.MemberDto;
import Portfolio.Missing_Animal.dto.RegisterDto;
import Portfolio.Missing_Animal.dto.ReportDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  컬렉션 최적화
 *  -> [1:다] 관계에서의 [다] 조회, 즉 컬렉션을 new 연산자로 [직접] DTO로 반환받기!!(내 포폴에서는 굳이 [직접] DTO로 받아가면서까지 컬렉션을 최적화시키지 X.
 *  Default batch fetch size 설정으로 컬렉션 최적화하는 게 더 적절하므로, 여기에서는 구현하지 x.JPA 활용 2의 P30참조)
 *  컬렉션 최적화는 [1:다] 관계에서 1에 해당하는 엔티티를 조회하는 Repository가 있고, 거기서 [다]에 해당하는 컬렉션을 조회할 필요가 있을 때 하는 최적화이다.
 *  고로, [다]에 해당하는 Register를 조회하는 Register(Query)Repository에서는 컬렉션 최적화라는 것이 성립이 안 된다.
 *  (root 쿼리 : 예를 들어,MemberRepository이면, Member를 조회하는 쿼리(ex.  SELECT m FROM Member m ) )
 *
 */


@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;

    public List<Member> findAllMembers() { // [페이징 불가능](findAllMembers4() : 최종 최적화 메서드)

    return em.createQuery("SELECT m FROM Member m"+

            " JOIN FETCH m.registers r" // 컬렉션을 fetch join하게 되면 페이징 불가능
            // " JOIN FETCH m.report rp" // 불가능 : 컬렉션 1개에 대해서만 fetch join과 default size에 의한 지연 로딩이 일어난다.
                    ,Member.class)       // (FETCH JOIN 컬렉션이 있으면, Default batch size를 설정을 해놔도 report에 대한 지연로딩이 일어나지x)
            .getResultList();            // 만약 컬렉션이 2개 이상 있으면, default batch size에 대한 지연로딩이 일어 나지 않는다.
    }

    public Member findMemberWithOneUserId(String userId) throws NonUniqueResultException, NoResultException {

         return em.createQuery("SELECT m FROM Member m" + // Register,Report 컬렉션에 대해, default batch size에 의한 지연로딩이 일어날 것 같지만
                                                                // 컬렉션이 2개 이상일 때에는 default batch size에 의해 지연 로딩이 일어나지 x.
                                                                // 또한 fetch join은 컬렉션에 대해서는 딱 1개에만 쓸 수가 있다.
                        " WHERE m.userId=:userId",Member.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }

    public MemberDto findMemberWithOneUserId2(String userId) throws NonUniqueResultException, NoResultException{

        MemberDto memberDto = em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                                "MemberDto(m.id,m.userId,m.username,m.email,m.phoneNumber)" +

                                " FROM Member m" +
                                " WHERE m.userId=:userId" // Member에 대해서 @xToOne 관계인 엔티티는 없다.

                        , MemberDto.class)
                .setParameter("userId", userId)
                .getSingleResult();

        List<Long> memberIds = new ArrayList<>();
        Long memberId = memberDto.getMemberId();
        memberIds.add(memberId);

        Map<Long, List<RegisterDto>> registerMap = findRegisterMap(memberIds); // 컬렉션 Registers에 대한 조회
        Map<Long, List<ReportDto>> reportMap = findReportMap(memberIds); // 컬렉션 Reports에 대한 조회

        List<RegisterDto> registerDtos = registerMap.get(memberId);
        List<ReportDto> reportDtos = reportMap.get(memberId);

        memberDto.setRegisters(registerDtos);
        memberDto.setReports(reportDtos);

        return memberDto;
    }


    public List<Member> findAllMembers2() { // [페이징 가능]

        return em.createQuery("SELECT m FROM Member m"

                                //" JOIN FETCH m.registers r" // 컬렉션을 fetch join하게 되면 페이징 불가능하므로, default batch fetch size로 [지연 로딩]을 통해

                        ,Member.class)
                .getResultList();
    }



    // new DTO로 직접 [컬렉션]을 [따로] 조회 1
    public List<MemberDto> findAllMembers3() { // (1+N) 문제 발생

        // 루트 조회(toOne 코드를 모두 한 번에 조회)
        List<MemberDto> members = findMembers(); // @xToOne 엔티티를  inner join으로 먼저 조회!
                                                 // fetch join을 쓸 필요가 없다.
                                                // 왜냐하면, new DTO(~~) 안의 매개 변수에 맞춰서, 특정값만 들고 오니깐,
                                                // FETCH JOIN처럼 모~든 값을 들고올 필요가 X.

        //  컬렉션을 [따로] 조회를 하여 MemberDTO의 SETTER를 활용을 하여 컬렉션을 set한다.
        // 그런데 이 과정에서 컬렉션을 조회할 때마다 쿼리문이 추가가 되기 때문에 [1+N] 문제가 발생을 한다.
        members.forEach(memberDto -> {

            List<RegisterDto> registers = findRegisters(memberDto.getMemberId());

            memberDto.setRegisters(registers);

        });

        return members;

    }

    public  List<MemberDto> findMembers( )
    {

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                                "MemberDto(m.id,m.userId,m.username,m.email,m.phoneNumber)" +

                                " FROM Member m" // Member에 대해서 @xToOne 관계인 엔티티는 없다.

                        ,MemberDto.class)
                .getResultList();

    }


    public List<RegisterDto> findRegisters(Long memberId){


        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                "RegisterDto(r.id,r.member.id,r.missingAddress.id,r.animalName,r.animalSex,r.animalAge,r.registerDate,r.registerStatus,r.reportedStatus)" +

                        " FROM Register r" +

                        " WHERE r.member.id =:id",RegisterDto.class)
                .setParameter("id",memberId)

                .getResultList();


    }


    // new DTO로 직접 [컬렉션]을 [따로] 조회 2( 컬렉션 조회 시, IN 쿼리에 Member의 id값들을 다 넣어서, 거기에 해당하는 Register를 추출)
    public List<MemberDto> findAllMembers4() { // (1+N) 문제 [해결]

        // root 조회(toOne 관계를 1번에 모두 조회)
        List<MemberDto> members = findMembers(); // toOne에 대한 조회

        List<Long> memberIds = toOrderIds(members);


        Map<Long,List<RegisterDto>> registerMap = findRegisterMap(memberIds); // toMany(컬렉션 Register)에 대한 조회 1.

        Map<Long,List<ReportDto>> reportMap = findReportMap(memberIds); // toMany(컬렉션 Report)에 대한 조회 2


        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        members.forEach(memberDto -> {

            Long id = memberDto.getMemberId();

            List<RegisterDto> registerDto = registerMap.get(id);
            List<ReportDto> reportDto = reportMap.get(id);

            memberDto.setRegisters(registerDto);
            memberDto.setReports(reportDto);
        });

        return members;

    }

    // List<MemberDto>에 있는 모든 id값들을 추출하여, 다시 List<Long>으로 반환!
    private List<Long> toOrderIds(List<MemberDto> result) {

        return result.stream()

                .map(memberDto -> memberDto.getMemberId())

                .collect(Collectors.toList());
    }

    private Map<Long, List<RegisterDto>> findRegisterMap(List<Long> memberIds) { // Map<Long,...>을 사용하기에, Member에 대한 [페이징 가능]

        List<RegisterDto> orderItems = em.createQuery(

                        "SELECT new Portfolio.Missing_Animal.dto."+

                                "RegisterDto(r.id,r.member.id,r.missingAddress.id,r.animalName,r.animalSex,r.animalAge,r.registerDate,r.registerStatus,r.reportedStatus)"+

                        " FROM Register r" +
                                // IN 쿼리를 사용하여 Member의 id값들을 모두 다 넣는다 -> 1 번의 쿼리 호출로 RegisterDTO 다 들고 옴 -> [1 + N] 문제 해결됨!!
                                " WHERE r.member.id IN :memberIds", RegisterDto.class)

                .setParameter("memberIds", memberIds)

                .getResultList();

        // RegisterDto의 memberId값을 기준으로, RigserDto를 Grouping~~!!!
        return orderItems.stream()

                .collect(Collectors.groupingBy(RegisterDto::getMemberId));
    }

    public  Map<Long, List<ReportDto>> findReportMap(List<Long> memberIds){


        List<ReportDto> report = em.createQuery(

                        "SELECT new Portfolio.Missing_Animal.dto."+

                                "ReportDto(r.id,rg.id,m.id,r.findedTime,r.findedAddress)"+

                                " FROM Report r" +
                                " INNER JOIN r.register rg" +
                                " INNER JOIN r.member m" +

                                " WHERE r.member.id IN :memberIds", ReportDto.class)

                .setParameter("memberIds", memberIds)

                .getResultList();

        // ReportDto의 memberId값을 기준으로, ReportDto를 Grouping~~!!!
        return report.stream()

                .collect(Collectors.groupingBy(ReportDto::getMemberId));


    }

}
