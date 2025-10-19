package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.GeographicRegionDto;
import nais.sales.service.sales_service.model.GeographicRegion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeographicRegionDtoMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public GeographicRegionDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static GeographicRegion fromDtoToEntity(GeographicRegionDto dto) {
        return modelMapper.map(dto, GeographicRegion.class);
    }

    public static GeographicRegionDto fromEntityToDto(GeographicRegion geographicRegion) {
        return modelMapper.map(geographicRegion, GeographicRegionDto.class);
    }
}
