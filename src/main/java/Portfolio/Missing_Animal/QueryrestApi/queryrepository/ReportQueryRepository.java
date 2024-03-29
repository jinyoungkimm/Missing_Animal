package Portfolio.Missing_Animal.QueryrestApi.queryrepository;


import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.dto.*;
import Portfolio.Missing_Animal.spring_data_jpa.ReportRepositorySDJ;
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

import java.util.List;

@Repository
@RequiredArgsConstructor
@Primary
@Qualifier("JPAReportQueryRepository")
@LogTrace
public class ReportQueryRepository {

    private final EntityManager em;

    private final ReportRepositorySDJ reportRepositorySDJ;

    public List<ReportDto> findAll(){

        List<ReportDto> report = findReport(); // toOne에 대한 조회를 여기서 한 번에!!!

        return report;

    }

    public List<ReportDto> findAllWithPaging(int offset,int limit){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                        "ReportDto(r.id,rg.id,m.id,r.findedTime,r.findedAddress)" +
                        " FROM Report r" +
                        " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                        " INNER JOIN r.register rg" // toOne 관계는 여기서 바로 inner join
                ,ReportDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    public ReportDtoWithPagination findAllWithPaging2(int pageNumber, int size){

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Report> page = reportRepositorySDJ.findAll(pageRequest); // 이 시점에서 이미 toOne에 대한 조회가 left fetch join에 의해 됨.

        Page<ReportDto> pageDto = page.map(report -> {

            Member member = report.getMember(); // toOne
            Register register = report.getRegister(); // toOne

            return new ReportDto(report.getId(), register.getId(),member.getId(),report.getFindedTime(),report.getFindedAddress());
        });

        List<ReportDto> content = pageDto.getContent();


        // pagination 정보!

        int countCurrent = content.size();

        int pageNumberCurrent = page.getNumber() + 1; // JPA의 PAGE 번호는 0부터 시작!

        long countTotal = page.getTotalElements();

        int pageTotal = page.getTotalPages();

        int itemsCountPerPage = size;

        Pagination pagination = new Pagination(countCurrent,pageNumberCurrent,countTotal,pageTotal,itemsCountPerPage);

        ReportDtoWithPagination reportDtoWithPagination = new ReportDtoWithPagination(pagination,content);

        return reportDtoWithPagination;


    }

    public ReportDtoWithPagination findAllWithPaging3(Pageable pageable){


        int pageNumber = pageable.getPageNumber();
        int size = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<Report> page = reportRepositorySDJ.findAll(pageRequest); // 이 시점에서 이미 toOne에 대한 조회가 left fetch join에 의해 됨.

        Page<ReportDto> pageDto = page.map(report -> {

            Member member = report.getMember(); // toOne
            Register register = report.getRegister(); // toOne

            return new ReportDto(report.getId(), register.getId(),member.getId(),report.getFindedTime(),report.getFindedAddress());
        });

        List<ReportDto> content = pageDto.getContent();


        // pagination 정보!

        int countCurrent = content.size();

        int pageNumberCurrent = page.getNumber() + 1; // JPA의 PAGE 번호는 0부터 시작!

        long countTotal = page.getTotalElements();

        int pageTotal = page.getTotalPages();

        int itemsCountPerPage = size;

        Pagination pagination = new Pagination(countCurrent,pageNumberCurrent,countTotal,pageTotal,itemsCountPerPage);

        ReportDtoWithPagination reportDtoWithPagination = new ReportDtoWithPagination(pagination,content);

        return reportDtoWithPagination;


    }


    public ReportDto findById(Long id) throws NonUniqueResultException, NoResultException {

        ReportDto id1 = em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                                "ReportDto(r.id,rg.id,m.id,r.findedTime,r.findedAddress)" +
                                " FROM Report r" +
                                " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                                " INNER JOIN r.register rg" + // toOne 관계는 여기서 바로 inner join
                                " WHERE r.id = :id"
                        , ReportDto.class)
                .setParameter("id", id)
                .getSingleResult();

        return id1;

    }

    // @GetMapping("/{reportId}/image")에서 사용하기 위함!
    public Report findByIdV2(Long id) throws NonUniqueResultException, NoResultException{

        return em.createQuery("SELECT r FROM Report r WHERE r.id=:id", Report.class)
                .setParameter("id", id)
                .getSingleResult();

    }


    public List<ReportDto> findReport(){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                                "ReportDto(r.id,rg.id,m.id,r.findedTime,r.findedAddress)" +
                                " FROM Report r" +
                                " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                                " INNER JOIN r.register rg" // toOne 관계는 여기서 바로 inner join
                        ,ReportDto.class)
                .getResultList();


    }

}
