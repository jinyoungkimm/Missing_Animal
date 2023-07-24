package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.Report;
import Portfolio.Missing_Animal.repository.repositoryinterface.ReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepositorySDJ extends JpaRepository<Report,Long> {

    /**
     * [쿼리 메서드 기능]은 파라미터가 증가하면, 메서드 이름이 매우 지저분해진다.
     * 따라서, @Query에다가 JPQL을 직접 작성하여 메서드의 이름이 길어지는 것을 예방하자!
     *
     */
}
