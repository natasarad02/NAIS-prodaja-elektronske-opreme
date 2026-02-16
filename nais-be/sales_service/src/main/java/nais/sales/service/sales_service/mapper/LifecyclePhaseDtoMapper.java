package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.LifecyclePhaseDto;
import nais.sales.service.sales_service.model.LifecyclePhase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LifecyclePhaseDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public LifecyclePhaseDtoMapper(ModelMapper modelMapper) {
        LifecyclePhaseDtoMapper.modelMapper = modelMapper;
    }

    public static LifecyclePhase fromDtoToEntity(LifecyclePhaseDto dto) {
        return modelMapper.map(dto, LifecyclePhase.class);
    }

    public static LifecyclePhaseDto fromEntityToDto(LifecyclePhase entity) {
        return modelMapper.map(entity, LifecyclePhaseDto.class);
    }
}