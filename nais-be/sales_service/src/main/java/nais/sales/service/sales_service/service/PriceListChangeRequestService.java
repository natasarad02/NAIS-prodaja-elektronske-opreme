package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.PriceListDto;
import nais.sales.service.sales_service.model.PriceListChangeRequest;

import java.util.List;

public interface PriceListChangeRequestService {
    List<PriceListChangeRequest> findAllPending();

    PriceListChangeRequest findById(Long id);

    PriceListChangeRequest createDraft(Long priceListId, PriceListDto proposed, String createdBy);

    void approve(Long id);

    void reject(Long id);
}
