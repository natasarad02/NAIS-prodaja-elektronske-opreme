package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.BenefitTypeCountViewDto;
import nais.sales.service.sales_service.dto.PromotialOfferDto;
import nais.sales.service.sales_service.dto.PromotialOfferStatusCountDto;

import java.util.List;

public interface PromotialOfferService {

    PromotialOfferDto createPromotialOffer(PromotialOfferDto promotialOfferDto);

    PromotialOfferDto updatePromotialOffer(Long id, PromotialOfferDto promotialOfferDto);

    void deletePromotialOffer(Long id);

    List<PromotialOfferDto> getPromotialOffers();

    PromotialOfferDto getPromotialOfferById(Long id);

    Double getAverageOfferDurationDays();

    List<BenefitTypeCountViewDto> getBenefitTypeCounts();

    PromotialOfferStatusCountDto getOfferStatusCounts();
}
