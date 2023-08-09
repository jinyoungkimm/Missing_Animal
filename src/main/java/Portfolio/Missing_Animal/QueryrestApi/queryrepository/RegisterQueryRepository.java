package Portfolio.Missing_Animal.QueryrestApi.queryrepository;

import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.*;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Primary
@Qualifier("JPARegisterQueryRepository")
public class RegisterQueryRepository {
    private final EntityManager em;

    private final RegisterRepositorySDJ registerRepositorySDJ;

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


        List<Long> registerIds = toRegisterDtoIds(register);


        Map<Long, List<ReportDto>> reportMap = findReportMap(registerIds);// toMany(컬렉션)에 대한 조회

        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        register.forEach( registerDto -> {

            Long id = registerDto.getRegisterId();

            List<ReportDto> reportDto = reportMap.get(id);


            registerDto.setReports(reportDto);
        });


        return register;
    }

    public RegisterDtoWithPagination findAllRegisters2WithPaging(int pageNumber,int size){

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Register> page = registerRepositorySDJ.findAll(pageRequest); // 이 시점에서 이미 toOne에 대한 조회가 left fetch join에 의해 됨.

        Page<RegisterDto> pageDto = page.map(register -> {

            Member member = register.getMember(); // toOne
            MissingAddress missingAddress = register.getMissingAddress(); // toOne

            return new RegisterDto(register.getId(), member.getId(), missingAddress.getId(),
                    register.getAnimalName(), register.getAnimalSex(), register.getAnimalAge(), register.getRegisterDate(), register.getRegisterStatus(), register.getReportedStatus());

        });

        List<RegisterDto> content = pageDto.getContent(); // toOne : Member, MissingAddress에 대해 left fetch join 일어남!

        List<Long> registerDtoIds = toRegisterDtoIds(content);

        Map<Long, List<ReportDto>> reportMap = findReportMap(registerDtoIds); // toMany에 대한 조회


        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        content.forEach(registerDto -> {

            Long id = registerDto.getRegisterId();

            List<ReportDto> reportDtos = reportMap.get(id);

            registerDto.setReports(reportDtos);

        });

        // pagination 정보!



        int countCurrent = content.size();

        int pageNumberCurrent = page.getNumber() + 1; // JPA의 PAGE 번호는 0부터 시작!

        long countTotal = page.getTotalElements();

        int pageTotal = page.getTotalPages();

        int itemsCountPerPage = size;

        Pagination pagination = new Pagination(countCurrent,pageNumberCurrent,countTotal,pageTotal,itemsCountPerPage);

        RegisterDtoWithPagination registerDtoWithPagination = new RegisterDtoWithPagination(pagination,content);

        return registerDtoWithPagination;

    }


    public RegisterDtoWithPagination findAllRegisters3WithPaging(Pageable pageable){

        int pageNumber = pageable.getPageNumber();
        int size = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Register> page = registerRepositorySDJ.findAll(pageRequest); // 이 시점에서 이미 toOne에 대한 조회가 left fetch join에 의해 됨.

        Page<RegisterDto> pageDto = page.map(register -> {

            Member member = register.getMember(); // toOne
            MissingAddress missingAddress = register.getMissingAddress(); // toOne

            return new RegisterDto(register.getId(), member.getId(), missingAddress.getId(),
                    register.getAnimalName(), register.getAnimalSex(), register.getAnimalAge(), register.getRegisterDate(), register.getRegisterStatus(), register.getReportedStatus());

        });

        List<RegisterDto> content = pageDto.getContent(); // toOne : Member, MissingAddress에 대해 left fetch join 일어남!

        List<Long> registerDtoIds = toRegisterDtoIds(content);

        Map<Long, List<ReportDto>> reportMap = findReportMap(registerDtoIds); // toMany에 대한 조회


        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        content.forEach(registerDto -> {

            Long id = registerDto.getRegisterId();

            List<ReportDto> reportDtos = reportMap.get(id);

            registerDto.setReports(reportDtos);

        });

        // pagination 정보!



        int countCurrent = content.size();

        int pageNumberCurrent = page.getNumber() + 1; // JPA의 PAGE 번호는 0부터 시작!

        long countTotal = page.getTotalElements();

        int pageTotal = page.getTotalPages();

        int itemsCountPerPage = size;

        Pagination pagination = new Pagination(countCurrent,pageNumberCurrent,countTotal,pageTotal,itemsCountPerPage);

        RegisterDtoWithPagination registerDtoWithPagination = new RegisterDtoWithPagination(pagination,content);

        return registerDtoWithPagination;

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




    public List<Long> toRegisterDtoIds(List<RegisterDto> registerDtos){


        return registerDtos.stream()

                .map(registerDto -> registerDto.getRegisterId())

                .collect(Collectors.toList());

    }


    public Map<Long,List<ReportDto>> findReportMap(List<Long> registerIds){


        List<ReportDto> reportDtos = em.createQuery(

                        "SELECT new Portfolio.Missing_Animal.dto." +

                                "ReportDto(r.id,rg.id,m.id,r.findedTime,r.findedAddress)" +

                                " FROM Report r" +

                                " INNER JOIN r.member m" +
                                " INNER JOIN r.register rg" +

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

    public RegisterDto findRegisterWithOneId2(Long id) throws NonUniqueResultException, NoResultException {

        RegisterDto registerDto = em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                                "RegisterDto(r.id,m.id,mr.id,r.animalName,r.animalSex,r.animalAge,r.registerDate,r.registerStatus,r.reportedStatus)" +

                                " FROM Register r" +

                                " INNER JOIN r.member m" + // toOne에 대해서는 여기서 조인

                                " INNER JOIN r.missingAddress mr" + // toOne에 대해서는 여기서 조인
                                " WHERE r.id=:id"

                        , RegisterDto.class)
                .setParameter("id", id)
                .getSingleResult();


        Long registerId = registerDto.getRegisterId();
        List<Long> registerIds = new ArrayList<>();
        registerIds.add(registerId);

        Map<Long, List<ReportDto>> reportMap = findReportMap(registerIds);
        List<ReportDto> reportDtos = reportMap.get(registerId);

        registerDto.setReports(reportDtos);

        return registerDto;

    }

}
