package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.SpecialOfferDto;
import nais.sales.service.sales_service.model.SpecialOffer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecialOfferDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public SpecialOfferDtoMapper(ModelMapper modelMapper) {
        SpecialOfferDtoMapper.modelMapper = modelMapper;
    }

    public static SpecialOffer fromDtoToEntity(SpecialOfferDto dto) {
        return modelMapper.map(dto, SpecialOffer.class);
    }

    public static SpecialOfferDto fromEntityToDto(SpecialOffer entity) {
        return modelMapper.map(entity, SpecialOfferDto.class);
    }
}
