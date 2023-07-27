package Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_custom;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RegisterRepositorySDJCustom {

    public List<Tuple> searchRegisters(RegisterSearchCond registerSearchCond);

    public Page<Tuple> searchRegistersWithPaging(RegisterSearchCond registerSearchCond);

}
