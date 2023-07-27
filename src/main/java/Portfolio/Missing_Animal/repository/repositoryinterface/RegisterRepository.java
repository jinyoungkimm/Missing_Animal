package Portfolio.Missing_Animal.repository.repositoryinterface;

import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegisterRepository {

    public Long save(Register register);

    public void delete(Register register);

    public long count();

    public Register findById(Long id);

    public List<Register> findAll();

    public List<Register> findByAnimalName(String animalName);

    // 검색 조건을 이용하여 실종 리스트 조회(Paging도 이용)
    public Page<Tuple> searchRegistersWithPagingComplexV2(RegisterSearchCond registerSearchCond, Pageable pageable);


}
