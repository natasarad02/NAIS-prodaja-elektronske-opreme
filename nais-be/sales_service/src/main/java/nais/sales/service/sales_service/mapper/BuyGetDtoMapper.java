package nais.sales.service.sales_service.mapper;

import nais.sales.service.sales_service.dto.BuyGetDto;
import nais.sales.service.sales_service.model.BuyGet;
import nais.sales.service.sales_service.repository.GeographicRegionRepository;
import nais.sales.service.sales_service.repository.PriceListRepository;
import nais.sales.service.sales_service.repository.UserTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyGetDtoMapper {

    @Autowired
    private static ModelMapper modelMapper;

    @Autowired
    private final PriceListRepository priceListRepo;

    @Autowired
    private final GeographicRegionRepository regionRepo;

    @Autowired
    private final UserTypeRepository userTypeRepo;

//    @Autowired
//    private final VariantRepository productRepo;

    @Autowired
    public BuyGetDtoMapper(ModelMapper modelMapper, PriceListRepository priceListRepo, GeographicRegionRepository regionRepo, UserTypeRepository userTypeRepo) {
        BuyGetDtoMapper.modelMapper = modelMapper;
        this.priceListRepo = priceListRepo;
        this.regionRepo = regionRepo;
        this.userTypeRepo = userTypeRepo;
    }

    public BuyGet fromDtoToEntity(BuyGetDto dto) {
        BuyGet e = modelMapper.map(dto, BuyGet.class);

        if (dto.getPriceListId() != null) {
            e.setPriceList(priceListRepo.getReferenceById(dto.getPriceListId()));
        }


//        var buyIds = dto.getProductsToBuyIds() == null ? List.<Long>of() : dto.getProductsToBuyIds();
//        e.setProductsToBuy(buyIds.stream()
//                .map(productRepo::getReferenceById)
//                .collect(java.util.stream.Collectors.toList()));
//
//        var getIds = dto.getProductsToGetIds() == null ? List.<Long>of() : dto.getProductsToGetIds();
//        e.setProductsToGet(getIds.stream()
//                .map(productRepo::getReferenceById)
//                .collect(java.util.stream.Collectors.toList()));

        return e;
    }

    public BuyGetDto fromEntityToDto(BuyGet e) {
        BuyGetDto dto = modelMapper.map(e, BuyGetDto.class);

        dto.setPriceListId(e.getPriceList() != null ? e.getPriceList().getId() : null);

//        dto.setProductsToBuyIds(e.getProductsToBuy() == null ? List.of()
//                : e.getProductsToBuy().stream().map(Variant::getId).collect(java.util.stream.Collectors.toList()));
//
//        dto.setProductsToGetIds(e.getProductsToGet() == null ? List.of()
//                : e.getProductsToGet().stream().map(Variant::getId).collect(java.util.stream.Collectors.toList()));

        return dto;
    }
}
