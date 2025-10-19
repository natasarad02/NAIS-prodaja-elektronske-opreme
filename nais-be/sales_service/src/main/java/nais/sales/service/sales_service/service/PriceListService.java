package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.PriceListDto;

import java.util.List;

public interface PriceListService {
    PriceListDto create(PriceListDto dto);

    List<PriceListDto> findAll();

    PriceListDto update(Long id, PriceListDto dto);

    PriceListDto findById(Long id);

    void delete(Long id);
}
