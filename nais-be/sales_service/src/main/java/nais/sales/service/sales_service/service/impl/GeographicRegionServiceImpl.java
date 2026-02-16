package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.GeographicRegionDto;
import nais.sales.service.sales_service.mapper.GeographicRegionDtoMapper;
import nais.sales.service.sales_service.model.GeographicRegion;
import nais.sales.service.sales_service.repository.GeographicRegionRepository;
import nais.sales.service.sales_service.service.GeographicRegionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeographicRegionServiceImpl implements GeographicRegionService {

    private final GeographicRegionRepository repository;

    public GeographicRegionServiceImpl(GeographicRegionRepository repository) {
        this.repository = repository;
    }

    @Override
    public GeographicRegionDto create(GeographicRegionDto dto) {
        GeographicRegion entity = GeographicRegionDtoMapper.fromDtoToEntity(dto);
        GeographicRegion saved = repository.save(entity);
        return GeographicRegionDtoMapper.fromEntityToDto(saved);
    }

    @Override
    public List<GeographicRegionDto> getAll() {
        return repository.findAll()
                .stream()
                .map(GeographicRegionDtoMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GeographicRegionDto update(Long id, GeographicRegionDto dto) {
        GeographicRegion entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GeographicRegion not found: " + id));

        entity.setName(dto.getName());
        entity.setCities(dto.getCities() == null ? null : new HashSet<>(dto.getCities()));
        entity.setCountries(dto.getCountries() == null ? null : new HashSet<>(dto.getCountries()));

        GeographicRegion saved = repository.save(entity);
        return GeographicRegionDtoMapper.fromEntityToDto(saved);
    }


    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("GeographicRegion not found: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public GeographicRegionDto getById(Long id) {
        GeographicRegion entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GeographicRegion not found: " + id));
        return GeographicRegionDtoMapper.fromEntityToDto(entity);
    }
}
