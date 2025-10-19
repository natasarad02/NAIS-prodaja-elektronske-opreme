package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.SpecialBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialBenefitRepository extends JpaRepository<SpecialBenefit, Long> {

}
