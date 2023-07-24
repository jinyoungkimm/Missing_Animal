package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.MissingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissingAddressRepositorySDJ extends JpaRepository<MissingAddress,Long> {
}
