package Portfolio.Missing_Animal.restapi.queryrepository;


import Portfolio.Missing_Animal.dto.ReportDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportQueryRepository {

    private final EntityManager em;

    public List<ReportDto> findAll(){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +
                        "ReportDto(r.id,rg.id,m.id,r.findedTime)" +
                        " FROM Report r" +
                                " INNER JOIN r.member m" + // toOne 관계는 여기서 바로 inner join
                                " INNER JOIN r.register rg" // toOne 관계는 여기서 바로 inner join
                ,ReportDto.class).getResultList();

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


    public ReportDto findById(Long id){

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

}
