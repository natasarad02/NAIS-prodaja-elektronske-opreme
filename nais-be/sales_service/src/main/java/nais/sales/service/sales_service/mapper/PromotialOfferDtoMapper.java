package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.PromotialOfferDto;
import nais.sales.service.sales_service.model.PromotialOffer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotialOfferDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public PromotialOfferDtoMapper(ModelMapper modelMapper) {
        PromotialOfferDtoMapper.modelMapper = modelMapper;
    }

    public static PromotialOffer fromDtoToEntity(PromotialOfferDto dto) {
        return modelMapper.map(dto, PromotialOffer.class);
    }

    public static PromotialOfferDto fromEntityToDto(PromotialOffer entity) {
        return modelMapper.map(entity, PromotialOfferDto.class);
    }
}

