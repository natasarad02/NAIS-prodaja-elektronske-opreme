package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.dto.UserTypeDto;

import java.util.List;

public interface UserTypeService {

    UserTypeDto create(UserTypeDto dto);

    List<UserTypeDto> findAll();

    UserTypeDto findById(Long id);

    UserTypeDto update(Long id, UserTypeDto dto);

    void delete(Long id);
}
