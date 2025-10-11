package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.SpecialOfferDto;
import nais.sales.service.sales_service.model.SpecialOffer;
import nais.sales.service.sales_service.repository.SpecialOfferRepository;
import nais.sales.service.sales_service.service.SpecialOfferService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialOfferServiceImpl implements SpecialOfferService {

    private final SpecialOfferRepository repository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public SpecialOfferDto createSpecialOffer(SpecialOfferDto dto) {

        SpecialOffer entity = modelMapper.map(dto, SpecialOffer.class);
        entity.setId(null);
        SpecialOffer saved = repository.save(entity);
        return modelMapper.map(saved, SpecialOfferDto.class);
    }


    @Override
    @Transactional
    public SpecialOfferDto updateSpecialOffer(SpecialOfferDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Id je obavezan za update.");
        }

        SpecialOffer existing = repository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("SpecialOffer ne postoji: id=" + dto.getId()));

        modelMapper.map(dto, existing);

        SpecialOffer saved = repository.save(existing);
        return modelMapper.map(saved, SpecialOfferDto.class);
    }


    @Override
    @Transactional
    public void deleteSpecialOffer(SpecialOfferDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Id je obavezan za delete.");
        }

        if (!repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("SpecialOffer ne postoji: id=" + dto.getId());
        }
        repository.deleteById(dto.getId());
    }

    @Override
    @Transactional()
    public List<SpecialOfferDto> getAllSpecialOffers() {
        return repository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, SpecialOfferDto.class))
                .toList();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public SpecialOfferDto getSpecialOfferById(Long id) {
        SpecialOffer so = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpecialOffer ne postoji: id=" + id));

        SpecialOfferDto dto = modelMapper.map(so, SpecialOfferDto.class);
        if (so.getBenefit() != null) dto.setBenefitId(so.getBenefit().getId());
        return dto;
    }
}

