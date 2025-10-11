package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.PercentageBenefitDto;
import nais.sales.service.sales_service.model.PercentageBenefit;
import nais.sales.service.sales_service.repository.GeographicRegionRepository;
import nais.sales.service.sales_service.repository.PriceListRepository;
import nais.sales.service.sales_service.repository.UserTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PercentageBenefitDtoMapper {

    private static ModelMapper modelMapper;
    private final PriceListRepository priceListRepo;
    private final GeographicRegionRepository regionRepo;
    private final UserTypeRepository userTypeRepo;

    @Autowired
    public PercentageBenefitDtoMapper(ModelMapper modelMapper, PriceListRepository priceListRepo, GeographicRegionRepository regionRepo, UserTypeRepository userTypeRepo) {
        PercentageBenefitDtoMapper.modelMapper = modelMapper;
        this.priceListRepo = priceListRepo;
        this.regionRepo = regionRepo;
        this.userTypeRepo = userTypeRepo;
    }

    public PercentageBenefit fromDtoToEntity(PercentageBenefitDto dto) {
        PercentageBenefit e = modelMapper.map(dto, PercentageBenefit.class);

        if (dto.getPriceListId() != null) {
            e.setPriceList(priceListRepo.getReferenceById(dto.getPriceListId()));
        }

        return e;
    }

    public PercentageBenefitDto fromEntityToDto(PercentageBenefit e) {
        PercentageBenefitDto dto = modelMapper.map(e, PercentageBenefitDto.class);

        dto.setPriceListId(e.getPriceList() != null ? e.getPriceList().getId() : null);

        return dto;
    }
}
