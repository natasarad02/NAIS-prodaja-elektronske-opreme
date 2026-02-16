package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Integer> {
}
