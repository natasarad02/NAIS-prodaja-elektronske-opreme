package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.AdministratorNotifyDto;
import nais.sales.service.sales_service.enums.NotificationStatus;
import nais.sales.service.sales_service.mapper.AdministratorNotifyDtoMapper;
import nais.sales.service.sales_service.model.AdministratorNotify;
import nais.sales.service.sales_service.repository.AdministratorNotifyRepository;
import nais.sales.service.sales_service.repository.PriceListRepository;
import nais.sales.service.sales_service.service.AdministratorNotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorNotifyServiceImpl implements AdministratorNotifyService {

    private final AdministratorNotifyRepository notifyRepo;
    private final PriceListRepository priceListRepo;
    private final AdministratorNotifyDtoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AdministratorNotifyDto> findAll() {
        return notifyRepo.findAll()
                .stream()
                .map(mapper::fromEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AdministratorNotifyDto findById(Long id) {
        var entity = notifyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AdministratorNotify nije pronađen: id=" + id));
        return mapper.fromEntityToDto(entity);
    }

    @Override
    @Transactional
    public AdministratorNotifyDto create(AdministratorNotifyDto dto) {
        if (dto.getPriceListId() == null) {
            throw new IllegalArgumentException("priceListId je obavezan.");
        }
        if (dto.getMessage() == null || dto.getMessage().isBlank()) {
            throw new IllegalArgumentException("message je obavezan.");
        }

        var entity = new AdministratorNotify();
        entity.setId(null);
        entity.setMessage(dto.getMessage());
        entity.setStatus(NotificationStatus.PENDING);
        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        entity.setPriceList(priceListRepo.getReferenceById(dto.getPriceListId()));

        var saved = notifyRepo.save(entity);
        return mapper.fromEntityToDto(saved);
    }

    @Override
    @Transactional
    public AdministratorNotifyDto update(Long id, AdministratorNotifyDto dto) {
        var entity = notifyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AdministratorNotify nije pronađen: id=" + id));

        if (dto.getMessage() != null) {
            entity.setMessage(dto.getMessage());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getPriceListId() != null) {
            entity.setPriceList(priceListRepo.getReferenceById(dto.getPriceListId()));
        }

        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }

        var saved = notifyRepo.save(entity);
        return mapper.fromEntityToDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!notifyRepo.existsById(id)) {
            throw new IllegalArgumentException("AdministratorNotify ne postoji: id=" + id);
        }
        notifyRepo.deleteById(id);
    }

}
