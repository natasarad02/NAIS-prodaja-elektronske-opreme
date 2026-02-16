package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.PriceListDto;
import nais.sales.service.sales_service.model.GeographicRegion;
import nais.sales.service.sales_service.model.PriceList;
import nais.sales.service.sales_service.model.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PriceListDtoMapper {

    private final ModelMapper modelMapper;

    public PriceListDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PriceList fromDtoToEntity(PriceListDto dto) {
        if (dto == null) return null;
        PriceList entity = modelMapper.map(dto, PriceList.class);

        if (entity.getItems() == null) {
            entity.setItems(new ArrayList<>());
        }
        return entity;
    }


    public PriceListDto fromEntityToDto(PriceList entity) {
        if (entity == null) return null;

        PriceListDto dto = modelMapper.map(entity, PriceListDto.class);

        dto.setCurrentPhaseId(entity.getCurrentPhase() != null ? entity.getCurrentPhase().getId() : null);

        if (entity.getRegions() != null) {
            dto.setRegionIds(
                    entity.getRegions().stream()
                            .map(GeographicRegion::getId)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setRegionIds(Set.of());
        }

        if (entity.getUserTypes() != null) {
            dto.setUserTypeIds(
                    entity.getUserTypes().stream()
                            .map(UserType::getId)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setUserTypeIds(Set.of());
        }

        if (entity.getItems() != null) {
            dto.setItems(
                    entity.getItems().stream()
                            .map(PriceListItemDtoMapper::fromEntityToDto)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setItems(Set.of());
        }

        return dto;
    }
}