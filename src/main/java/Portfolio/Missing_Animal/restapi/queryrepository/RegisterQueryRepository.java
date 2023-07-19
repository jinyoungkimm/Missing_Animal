package Portfolio.Missing_Animal.restapi.queryrepository;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.RegisterDto;
import Portfolio.Missing_Animal.dto.ReportDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RegisterQueryRepository {
    private final EntityManager em;

    /**
     * @xToOne 관계의 엔티티를 사용할지 안 할지는 모르지만 어차피 fetch join을 사용하면, 1번의 쿼리로 가져올 수 있으니, 일단은 가져 오도록 하자!
     * @return
     */
    public List<Register> findAllRegisters(){

        return em.createQuery("SELECT r FROM Register r" +

                " JOIN FETCH r.member m" + // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                " JOIN FETCH r.missingAddress mr" // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                        ,Register.class)
                .getResultList();

    }


    public List<RegisterDto> findAllRegisters2(){

        List<RegisterDto> register = findRegister(); // toOne에 대한 조회!


        List<Long> registerIds = toRegisterIds(register);



        Map<Long, List<ReportDto>> reportMap = findReportMap(registerIds);// toMany(컬렉션)에 대한 조회

        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        register.forEach( registerDto -> {

            Long id = registerDto.getRegisterId();

            List<ReportDto> reportDto = reportMap.get(id);


            registerDto.setReports(reportDto);
        });


        return register;
    }

    public List<RegisterDto> findRegister(){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                "RegisterDto(r.id,m.id,mr.id,r.animalName,r.animalSex,r.animalAge,r.registerDate,r.registerStatus,r.reportedStatus)" +

                " FROM Register r" +

                " INNER JOIN r.member m" + // toOne에 대해서는 여기서 조인

                " INNER JOIN r.missingAddress mr " // toOne에 대해서는 여기서 조인

                        ,RegisterDto.class)
                .getResultList();

    }




    public List<Long> toRegisterIds(List<RegisterDto> registerDtos){


        return registerDtos.stream()

                .map(registerDto -> registerDto.getRegisterId())

                .collect(Collectors.toList());

    }

    public Map<Long,List<ReportDto>> findReportMap(List<Long> registerIds){


        List<ReportDto> reportDtos = em.createQuery(

                        "SELECT new Portfolio.Missing_Animal.dto." +

                                "ReportDto(r.register.id)" +

                                " FROM Report r" +

                                " WHERE r.register.id IN :registerIds", ReportDto.class)

                .setParameter("registerIds", registerIds)

                .getResultList();


        return reportDtos.stream()

                    .collect(

                            Collectors.groupingBy

                                    (ReportDto::getRegisterId)
                    );


    }



    public List<Register> findAllRegistersWithPaging(int offset,int limit){

        return em.createQuery("SELECT r FROM Register r" +

                                " JOIN FETCH r.member m" + // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                                " JOIN FETCH r.missingAddress mr" // @xToOne 관계의 엔티티는 fetch join으로 최적화를 하면 된다.
                        ,Register.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();


    }

    public Register findRegisterWithOneId(Long id){

        Register register = em.find(Register.class, id);

        return register;


    }




}
