package Portfolio.Missing_Animal.repository;


import Portfolio.Missing_Animal.domain.Register;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegisterApiRepository {

    private final EntityManager em;

    public List<Register> allRegisters(){

        return em.createQuery("SELECT r FROM Register r",Register.class)
                .getResultList();

    }





}
