package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.enums.NotificationStatus;
import nais.sales.service.sales_service.model.PriceListChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceListChangeRequestRepository extends JpaRepository<PriceListChangeRequest, Long> {
    List<PriceListChangeRequest> findAllByStatus(NotificationStatus status);

    List<PriceListChangeRequest> findAllByPriceList_Id(Long priceListId);
}

