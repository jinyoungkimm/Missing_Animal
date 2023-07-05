package Portfolio.Missing_Animal.SovledIncidentRepository;


import jakarta.persistence.EntityManager;
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
