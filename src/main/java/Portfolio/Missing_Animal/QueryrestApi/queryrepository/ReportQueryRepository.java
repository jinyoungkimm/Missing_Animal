package Portfolio.Missing_Animal.QueryrestApi.queryrepository;


import Portfolio.Missing_Animal.dto.ReportDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportQueryRepository {

    private final EntityManager em;

    public List<ReportDto> findAll(){

        List<ReportDto> report = findReport(); // toOne에 대한 조회를 여기서 한 번에!!!

        return report;

    }

    public List<ReportDto> findAllWithPaging(int offset,int limit){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                        "ReportDto(r.id,rg.id,m.id,r.findedTime)" +
                        " FROM Report r" +
                        " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                        " INNER JOIN r.register rg" // toOne 관계는 여기서 바로 inner join
                ,ReportDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }


    public ReportDto findById(Long id) throws NonUniqueResultException, NoResultException {

        ReportDto id1 = em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                                "ReportDto(r.id,rg.id,m.id,m.userId,r.findedTime)" +
                                " FROM Report r" +
                                " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                                " INNER JOIN r.register rg" + // toOne 관계는 여기서 바로 inner join
                                " WHERE r.id = :id"
                        , ReportDto.class)
                .setParameter("id", id)
                .getSingleResult();

        return id1;

    }

    public List<ReportDto> findReport(){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                                "ReportDto(r.id,rg.id,m.id,r.findedTime)" +
                                " FROM Report r" +
                                " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                                " INNER JOIN r.register rg" // toOne 관계는 여기서 바로 inner join
                        ,ReportDto.class)
                .getResultList();


    }

}
