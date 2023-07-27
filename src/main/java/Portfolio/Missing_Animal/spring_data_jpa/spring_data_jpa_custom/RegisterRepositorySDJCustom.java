package Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_custom;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegisterRepositorySDJCustom {

    public List<Tuple> searchRegisters(RegisterSearchCond registerSearchCond);

    // fetchResults() 사용!!
    public Page<Tuple> searchRegistersWithPagingSimple(RegisterSearchCond registerSearchCond, Pageable pageable);

    // fetch()로 내용을 조회하고, fetchCount()로 count 쿼리를 구함
    // 즉, 내용 조회와 count 쿼리를 분리!
    public Page<Tuple> searchRegistersWithPagingComplexV1(RegisterSearchCond registerSearchCond,Pageable pageable);

    // count 쿼리를 더 가독성 있게 구현!
    public Page<Tuple> searchRegistersWithPagingComplexV2(RegisterSearchCond registerSearchCond,Pageable pageable);

}
