package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.SpecialBenefitDto;
import nais.sales.service.sales_service.model.SpecialBenefit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecialBenefitDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public SpecialBenefitDtoMapper(ModelMapper modelMapper) {
        SpecialBenefitDtoMapper.modelMapper = modelMapper;
    }

    public static SpecialBenefit fromDtoToEntity(SpecialBenefitDto dto) {
        return modelMapper.map(dto, SpecialBenefit.class);
    }

    public static SpecialBenefitDto fromEntityToDto(SpecialBenefit entity) {
        return modelMapper.map(entity, SpecialBenefitDto.class);
    }
}

