package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.SpecialOfferDto;

import java.util.List;


public interface SpecialOfferService {

    SpecialOfferDto createSpecialOffer(SpecialOfferDto dto);

    SpecialOfferDto updateSpecialOffer(SpecialOfferDto dto);

    void deleteSpecialOffer(SpecialOfferDto dto);

    List<SpecialOfferDto> getAllSpecialOffers();

    SpecialOfferDto getSpecialOfferById(Long id);
}
