package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.SpecialBenefitDto;

import java.util.List;

public interface SpecialBenefitService {

    SpecialBenefitDto create(SpecialBenefitDto dto);

    //    TresholdPriceDto createTreshold(TresholdPriceDto dto);
//    PercentageBenefitDto createPercentage(PercentageBenefitDto dto);
    List<SpecialBenefitDto> findAll();
}
