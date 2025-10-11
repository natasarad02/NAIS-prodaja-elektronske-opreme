package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.GeographicRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeographicRegionRepository extends JpaRepository<GeographicRegion, Long> {

}
