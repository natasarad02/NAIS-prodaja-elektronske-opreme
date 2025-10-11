package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.PriceListDto;
import nais.sales.service.sales_service.dto.PriceListItemDto;
import nais.sales.service.sales_service.enums.NotificationStatus;
import nais.sales.service.sales_service.mapper.PriceListDtoMapper;
import nais.sales.service.sales_service.model.PriceListChangeRequest;
import nais.sales.service.sales_service.model.PriceListItem;
import nais.sales.service.sales_service.repository.*;
import nais.sales.service.sales_service.service.PriceListChangeRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceListChangeRequestServiceImpl implements PriceListChangeRequestService {

    private final PriceListRepository priceListRepo;
    private final GeographicRegionRepository regionRepo;
    private final UserTypeRepository userTypeRepo;
    private final LifecyclePhaseRepository phaseRepo;
    private final PriceListChangeRequestRepository changeRepo;
    private final PriceListDtoMapper dtoMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PriceListChangeRequest> findAllPending() {
        return changeRepo.findAllByStatus(NotificationStatus.PENDING);
    }

    @Override
    @Transactional(readOnly = true)
    public PriceListChangeRequest findById(Long id) {
        return changeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ChangeRequest not found: id=" + id));
    }

    @Override
    @Transactional
    public PriceListChangeRequest createDraft(Long priceListId, PriceListDto proposed, String createdBy) {
        var pl = priceListRepo.findById(priceListId)
                .orElseThrow(() -> new IllegalArgumentException("PriceList not found: " + priceListId));

        try {
            var json = objectMapper.writeValueAsString(proposed);
            var cr = PriceListChangeRequest.builder()
                    .priceList(pl)
                    .proposedPayload(json)
                    .status(NotificationStatus.PENDING)
                    .createdBy(createdBy)
                    .createdAt(LocalDateTime.now())
                    .build();
            return changeRepo.save(cr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot serialize proposal", e);
        }
    }

    @Override
    @Transactional
    public void approve(Long id) {
        var cr = findById(id);
        if (cr.getStatus() != NotificationStatus.PENDING) return;

        PriceListDto dto;
        try {
            dto = objectMapper.readValue(cr.getProposedPayload(), PriceListDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid proposed payload JSON", e);
        }

        var pl = priceListRepo.findById(cr.getPriceList().getId())
                .orElseThrow(() -> new IllegalStateException("Original PriceList missing"));

        if (dto.getTitle() != null) pl.setTitle(dto.getTitle());
        if (dto.getDiscount() != null) pl.setDiscount(dto.getDiscount());
        if (dto.getQuantity() != null) pl.setQuantity(dto.getQuantity());
        if (dto.getStartDate() != null) pl.setStartDate(dto.getStartDate());
        if (dto.getExpireDate() != null) pl.setExpireDate(dto.getExpireDate());

        if (dto.getCurrentPhaseId() != null) {
            var phase = phaseRepo.findById(dto.getCurrentPhaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Phase not found: " + dto.getCurrentPhaseId()));
            pl.setCurrentPhase(phase);
        }

        if (dto.getRegionIds() != null) {
            var regions = dto.getRegionIds().stream()
                    .map(rid -> regionRepo.findById(rid)
                            .orElseThrow(() -> new IllegalArgumentException("Region not found: " + rid)))
                    .collect(Collectors.toList());

            pl.getRegions().clear();
            pl.getRegions().addAll(regions);
        }

        if (dto.getUserTypeIds() != null) {
            var uts = dto.getUserTypeIds().stream()
                    .map(uid -> userTypeRepo.findById(uid)
                            .orElseThrow(() -> new IllegalArgumentException("UserType not found: " + uid)))
                    .collect(Collectors.toList());

            pl.getUserTypes().clear();
            pl.getUserTypes().addAll(uts);
        }

        if (dto.getItems() != null) {
            // 🔸 Mapiramo postojeće stavke po productId radi lakšeg ažuriranja
            Map<Long, PriceListItem> byProductId = pl.getItems().stream()
                    .collect(Collectors.toMap(PriceListItem::getProductId, it -> it));

            Set<Long> incoming = new HashSet<>();

            for (PriceListItemDto itDto : dto.getItems()) {
                Long productId = itDto.getProductId();
                incoming.add(productId);

                // Ako već postoji — ažuriraj cenu
                var existing = byProductId.get(productId);
                if (existing != null) {
                    existing.setPrice(itDto.getPrice());
                } else {
                    PriceListItem newItem = new PriceListItem();
                    newItem.setPriceList(pl);
                    newItem.setProductId(productId);
                    newItem.setPrice(itDto.getPrice());
                    pl.getItems().add(newItem);
                }
            }

            // 🔸 Ukloni sve proizvode koji nisu više u novom DTO-u
            pl.getItems().removeIf(it -> !incoming.contains(it.getProductId()));
        }

// 🔸 Snimi promene u PriceList i označi zahtev kao odobren
        priceListRepo.save(pl);
        cr.setStatus(NotificationStatus.APPROVED);
        changeRepo.save(cr);
    }

    @Override
    @Transactional
    public void reject(Long id) {
        var cr = findById(id);
        if (cr.getStatus() == NotificationStatus.PENDING) {
            cr.setStatus(NotificationStatus.REJECTED);
            changeRepo.save(cr);
        }
    }
}
