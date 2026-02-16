package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.LifecyclePhaseDto;

import java.util.List;

public interface LifecyclePhaseService {
    LifecyclePhaseDto create(LifecyclePhaseDto dto);

    List<LifecyclePhaseDto> findAll();

    void deleteById(Long id);

    LifecyclePhaseDto update(Long id, LifecyclePhaseDto dto);

    LifecyclePhaseDto findById(Long id);
}
