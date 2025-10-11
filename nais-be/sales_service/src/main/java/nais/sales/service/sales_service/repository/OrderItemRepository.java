package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

