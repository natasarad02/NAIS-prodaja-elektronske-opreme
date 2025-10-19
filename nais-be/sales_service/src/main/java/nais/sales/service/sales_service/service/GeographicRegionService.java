package nais.sales.service.sales_service.service;


import nais.sales.service.sales_service.dto.GeographicRegionDto;

import java.util.List;


public interface GeographicRegionService {
    GeographicRegionDto create(GeographicRegionDto dto);

    List<GeographicRegionDto> getAll();

    public GeographicRegionDto update(Long id, GeographicRegionDto dto);

    public void deleteById(Long id);

    GeographicRegionDto getById(Long id);
}
