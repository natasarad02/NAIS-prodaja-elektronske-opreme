package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.UserTypeDto;
import nais.sales.service.sales_service.model.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTypeDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public UserTypeDtoMapper(ModelMapper modelMapper) {
        UserTypeDtoMapper.modelMapper = modelMapper;
    }

    public static UserType fromDtoToEntity(UserTypeDto dto) {
        return modelMapper.map(dto, UserType.class);
    }

    public static UserTypeDto fromEntityToDto(UserType entity) {
        return modelMapper.map(entity, UserTypeDto.class);
    }
}