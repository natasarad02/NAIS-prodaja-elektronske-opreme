package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.PriceListItemDto;
import nais.sales.service.sales_service.model.PriceListItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceListItemDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public PriceListItemDtoMapper(ModelMapper modelMapper) {
        PriceListItemDtoMapper.modelMapper = modelMapper;
    }

    public static PriceListItem fromDtoToEntity(PriceListItemDto dto) {
        return modelMapper.map(dto, PriceListItem.class);
    }

    public static PriceListItemDto fromEntityToDto(PriceListItem entity) {
        return modelMapper.map(entity, PriceListItemDto.class);
    }
}
