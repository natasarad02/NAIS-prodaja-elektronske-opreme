package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.BuyGetDto;
import nais.sales.service.sales_service.dto.PercentageBenefitDto;
import nais.sales.service.sales_service.dto.SpecialBenefitDto;
import nais.sales.service.sales_service.dto.TresholdPriceDto;
import nais.sales.service.sales_service.mapper.BuyGetDtoMapper;
import nais.sales.service.sales_service.mapper.PercentageBenefitDtoMapper;
import nais.sales.service.sales_service.mapper.TresholdPriceDtoMapper;
import nais.sales.service.sales_service.model.BuyGet;
import nais.sales.service.sales_service.model.PercentageBenefit;
import nais.sales.service.sales_service.model.SpecialBenefit;
import nais.sales.service.sales_service.model.TresholdPrice;
import nais.sales.service.sales_service.repository.SpecialBenefitRepository;
import nais.sales.service.sales_service.service.SpecialBenefitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialBenefitServiceImpl implements SpecialBenefitService {

    private final SpecialBenefitRepository repository;

    private final TresholdPriceDtoMapper tresholdMapper;
    private final PercentageBenefitDtoMapper percentageMapper;
    private final BuyGetDtoMapper buyGetMapper;

    @Override
    @Transactional
    public SpecialBenefitDto create(SpecialBenefitDto dto) {
        if (dto == null) throw new IllegalArgumentException("Body is required");

        if (dto instanceof TresholdPriceDto t) {
            var e = tresholdMapper.fromDtoToEntity(t);
            return tresholdMapper.fromEntityToDto(repository.save(e));
        } else if (dto instanceof PercentageBenefitDto p) {
            var e = percentageMapper.fromDtoToEntity(p);
            return percentageMapper.fromEntityToDto(repository.save(e));
        } else if (dto instanceof BuyGetDto b) {
            var e = buyGetMapper.fromDtoToEntity(b);
            return buyGetMapper.fromEntityToDto(repository.save(e));
        }

        throw new IllegalArgumentException("Unsupported DTO subtype: " + dto.getClass().getName());
    }

    @Override
    @Transactional()
    public List<SpecialBenefitDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private SpecialBenefitDto toDto(SpecialBenefit e) {
        if (e instanceof TresholdPrice tp) {
            TresholdPriceDto d = tresholdMapper.fromEntityToDto(tp);
            d.setBenefitType("TRESHOLD");
            return d;
        } else if (e instanceof PercentageBenefit pb) {
            PercentageBenefitDto d = percentageMapper.fromEntityToDto(pb);
            d.setBenefitType("PERCENTAGE");
            return d;
        } else if (e instanceof BuyGet bg) {
            BuyGetDto d = buyGetMapper.fromEntityToDto(bg);
            d.setBenefitType("BUY_GET");
            return d;
        }
        throw new IllegalStateException("Unknown benefit subtype: " + e.getClass().getName());
    }
}
