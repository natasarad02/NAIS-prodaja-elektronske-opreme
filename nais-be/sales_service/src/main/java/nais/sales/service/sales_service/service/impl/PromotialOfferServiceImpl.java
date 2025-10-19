package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.BenefitTypeCountViewDto;
import nais.sales.service.sales_service.enums.OfferStatus;
import nais.sales.service.sales_service.dto.PromotialOfferDto;
import nais.sales.service.sales_service.dto.PromotialOfferStatusCountDto;
import nais.sales.service.sales_service.model.PromotialOffer;
import nais.sales.service.sales_service.repository.PromotialOfferRepository;
import nais.sales.service.sales_service.service.PromotialOfferService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotialOfferServiceImpl implements PromotialOfferService {

    private final PromotialOfferRepository promotialOfferRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PromotialOfferDto createPromotialOffer(PromotialOfferDto dto) {
        if (dto == null) throw new IllegalArgumentException("Body is required");

        PromotialOffer entity = modelMapper.map(dto, PromotialOffer.class);
        entity.setId(null);
        PromotialOffer saved = promotialOfferRepository.save(entity);

        return modelMapper.map(saved, PromotialOfferDto.class);
    }

    @Override
    @Transactional
    public PromotialOfferDto updatePromotialOffer(Long id, PromotialOfferDto dto) {
        if (id == null) throw new IllegalArgumentException("id is required");
        if (dto == null) throw new IllegalArgumentException("Body is required");

        PromotialOffer existing = promotialOfferRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotial offer not found: id=" + id));

        modelMapper.map(dto, existing);

        existing.setId(id);

        PromotialOffer saved = promotialOfferRepository.save(existing);
        return modelMapper.map(saved, PromotialOfferDto.class);
    }

    @Override
    @Transactional
    public void deletePromotialOffer(Long id) {

        if (!promotialOfferRepository.existsById(id)) {
            throw new IllegalArgumentException("Promotial offer not found: id=" + id);
        }
        promotialOfferRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotialOfferDto> getPromotialOffers() {
        return promotialOfferRepository.findAll()
                .stream()
                .map(e -> {
                    PromotialOfferDto dto = modelMapper.map(e, PromotialOfferDto.class);
                    dto.setStatus(resolve(e.getStartDate(), e.getExpiredDate()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PromotialOfferDto getPromotialOfferById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is required");
        PromotialOffer entity = promotialOfferRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotial offer not found: id=" + id));
        return modelMapper.map(entity, PromotialOfferDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageOfferDurationDays() {
        return promotialOfferRepository.avgDurationDays();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BenefitTypeCountViewDto> getBenefitTypeCounts() {
        return promotialOfferRepository.countByBenefitTypeRaw()
                .stream()
                .map(row -> {
                    String type = (String) row[0];
                    Long cnt = ((Number) row[1]).longValue();
                    return new BenefitTypeCountViewDto(type, cnt);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PromotialOfferStatusCountDto getOfferStatusCounts() {
        long planned = 0, active = 0, expired = 0, inactive = 0;

        for (PromotialOffer e : promotialOfferRepository.findAll()) {
            OfferStatus s = resolve(e.getStartDate(), e.getExpiredDate());
            switch (s) {
                case PLANNED -> planned++;
                case ACTIVE -> active++;
                case EXPIRED -> expired++;
                case INACTIVE -> inactive++;
            }
        }
        return new PromotialOfferStatusCountDto(active, planned, expired, inactive);
    }

    private OfferStatus resolve(LocalDate start, LocalDate end) {
        if (start == null || end == null) return OfferStatus.INACTIVE;
        LocalDate today = LocalDate.now();
        if (today.isBefore(start)) return OfferStatus.PLANNED;
        if (!today.isAfter(end)) return OfferStatus.ACTIVE;
        return OfferStatus.EXPIRED;
    }
}

