package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
