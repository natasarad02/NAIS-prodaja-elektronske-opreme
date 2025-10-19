package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.UserTypeDto;
import nais.sales.service.sales_service.mapper.UserTypeDtoMapper;
import nais.sales.service.sales_service.model.UserType;
import nais.sales.service.sales_service.repository.UserTypeRepository;
import nais.sales.service.sales_service.service.UserTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository _repository;

    public UserTypeServiceImpl(UserTypeRepository repository) {
        this._repository = repository;
    }

    @Override
    @Transactional
    public UserTypeDto create(UserTypeDto dto) {
        UserType entity = UserTypeDtoMapper.fromDtoToEntity(dto);
        UserType result = _repository.save(entity);
        return UserTypeDtoMapper.fromEntityToDto(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTypeDto> findAll() {
        return _repository.findAll()
                .stream()
                .map(UserTypeDtoMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserTypeDto findById(Long id) {
        UserType entity = _repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserType not found: " + id));
        return UserTypeDtoMapper.fromEntityToDto(entity);
    }

    @Override
    @Transactional
    public UserTypeDto update(Long id, UserTypeDto dto) {
        UserType entity = _repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserType not found: " + id));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        UserType saved = _repository.save(entity);
        return UserTypeDtoMapper.fromEntityToDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!_repository.existsById(id)) {
            throw new EntityNotFoundException("UserType not found: " + id);
        }
        _repository.deleteById(id);
    }
}
