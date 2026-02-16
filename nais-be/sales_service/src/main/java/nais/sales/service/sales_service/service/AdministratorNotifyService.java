package nais.sales.service.sales_service.service;


import nais.sales.service.sales_service.dto.AdministratorNotifyDto;

import java.util.List;

public interface AdministratorNotifyService {
    List<AdministratorNotifyDto> findAll();

    AdministratorNotifyDto findById(Long id);

    AdministratorNotifyDto create(AdministratorNotifyDto dto);

    AdministratorNotifyDto update(Long id, AdministratorNotifyDto dto);

    void delete(Long id);
}
