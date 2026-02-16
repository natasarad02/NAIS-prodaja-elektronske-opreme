package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.LifecyclePhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LifecyclePhaseRepository extends JpaRepository<LifecyclePhase, Long> {

    Optional<LifecyclePhase> findByPosition(Long position);

    @Query("select lp.position from LifecyclePhase lp order by lp.position asc")
    List<Integer> findAllPositionsAsc();
}
