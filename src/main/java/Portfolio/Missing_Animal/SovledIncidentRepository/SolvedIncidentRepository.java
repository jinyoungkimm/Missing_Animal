package Portfolio.Missing_Animal.SovledIncidentRepository;


import Portfolio.Missing_Animal.dto.SovledIncidentDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolvedIncidentRepository {

    private final EntityManager em;

    /*public SovledIncidentDto querySolvedIncident(){

        return em.createQuery( "select ",

                SovledIncidentDto.class); query
                .getSingleResult();


    }*/



}
