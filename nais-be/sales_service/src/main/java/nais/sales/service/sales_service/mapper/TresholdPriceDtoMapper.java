package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.TresholdPriceDto;
import nais.sales.service.sales_service.model.TresholdPrice;
import nais.sales.service.sales_service.repository.GeographicRegionRepository;
import nais.sales.service.sales_service.repository.PriceListRepository;
import nais.sales.service.sales_service.repository.UserTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TresholdPriceDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    private GeographicRegionRepository _regionRepository;

    @Autowired
    private PriceListRepository _priceListRepository;

    @Autowired
    private UserTypeRepository _userTypeRepository;


    @Autowired
    public TresholdPriceDtoMapper(ModelMapper modelMapper) {
        TresholdPriceDtoMapper.modelMapper = modelMapper;
    }

    public static TresholdPriceDto fromEntityToDto(TresholdPrice entity) {
        TresholdPriceDto dto = modelMapper.map(entity, TresholdPriceDto.class);

        dto.setPriceListId(entity.getPriceList() != null ? entity.getPriceList().getId() : null);

        return dto;
    }

    public TresholdPrice fromDtoToEntity(TresholdPriceDto dto) {
        TresholdPrice e = modelMapper.map(dto, TresholdPrice.class);

        if (dto.getPriceListId() != null) {
            e.setPriceList(_priceListRepository.getReferenceById(dto.getPriceListId()));
        }

        return e;
    }

}