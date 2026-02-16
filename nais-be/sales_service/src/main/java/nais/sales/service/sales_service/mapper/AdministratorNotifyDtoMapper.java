package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.AdministratorNotifyDto;
import nais.sales.service.sales_service.model.AdministratorNotify;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdministratorNotifyDtoMapper {

    private static ModelMapper modelMapper;

    public AdministratorNotifyDtoMapper() {
        modelMapper = new ModelMapper();
    }

    public static AdministratorNotify fromDtoToEntity(AdministratorNotifyDto dto) {
        return modelMapper.map(dto, AdministratorNotify.class);
    }

    public AdministratorNotifyDto fromEntityToDto(AdministratorNotify entity) {
        return modelMapper.map(entity, AdministratorNotifyDto.class);
    }
}
