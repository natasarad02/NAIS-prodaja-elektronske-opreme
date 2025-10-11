package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.OfferSimpleImpactDto;
import nais.sales.service.sales_service.repository.OfferOrderMatchRepository;
import nais.sales.service.sales_service.service.impl.OfferAttributionService;
import nais.sales.service.sales_service.service.impl.OfferImpactReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferImpactController {

    private final OfferAttributionService attributionService;
    private final OfferImpactReadService impactReadService;
    private final OfferOrderMatchRepository matchRepository;

    @GetMapping("/{offerId}/impact/simple")
    public OfferSimpleImpactDto getSimpleImpact(@PathVariable Long offerId) {

        attributionService.attributeOrdersForOffer(offerId);
        return impactReadService.getSimpleImpact(offerId);
    }

    @GetMapping("/{offerId}/matched-orders")
    public List<Long> getMatchedOrderIds(@PathVariable Long offerId) {

        attributionService.attributeOrdersForOffer(offerId);
        return matchRepository.findByIdOfferId(offerId).stream()
                .map(m -> m.getId().getOrderId())
                .toList();
    }
}
