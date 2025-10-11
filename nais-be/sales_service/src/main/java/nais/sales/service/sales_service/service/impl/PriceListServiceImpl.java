package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.PriceListDto;
import nais.sales.service.sales_service.dto.PriceListItemDto;
import nais.sales.service.sales_service.enums.NotificationStatus;
import nais.sales.service.sales_service.mapper.PriceListDtoMapper;
import nais.sales.service.sales_service.mapper.PriceListItemDtoMapper;
import nais.sales.service.sales_service.model.*;
import nais.sales.service.sales_service.repository.*;
import nais.sales.service.sales_service.service.PriceListChangeRequestService;
import nais.sales.service.sales_service.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PriceListServiceImpl implements PriceListService {

    private final PriceListRepository _repository;
    private final PriceListItemRepository _itemRepository;
    private final GeographicRegionRepository _regionRepository;
    private final UserTypeRepository _userTypeRepository;
    private final LifecyclePhaseRepository _lifecyclePhaseRepository;
    private final AdministratorNotifyRepository notifyRepo;
    private final PriceListChangeRequestService priceListChangeRequestService;

    @Autowired
    PriceListDtoMapper priceListDtoMapper;

    @Autowired
    PriceListItemDtoMapper priceListItemDtoMapper;

    public PriceListServiceImpl(PriceListRepository repository, PriceListItemRepository itemRepository, GeographicRegionRepository regionRepository, UserTypeRepository userTypeRepository, LifecyclePhaseRepository lifecyclePhaseRepository, AdministratorNotifyRepository notifyRepo, PriceListChangeRequestService priceListChangeRequestService) {
        this._repository = repository;
        this._itemRepository = itemRepository;
        this._regionRepository = regionRepository;
        this._userTypeRepository = userTypeRepository;
        this._lifecyclePhaseRepository = lifecyclePhaseRepository;
        this.notifyRepo = notifyRepo;
        this.priceListChangeRequestService = priceListChangeRequestService;
    }


    @Override
    @Transactional
    public PriceListDto create(PriceListDto dto) {

        PriceList entity = priceListDtoMapper.fromDtoToEntity(dto);
        entity.setItems(new ArrayList<>());

        if (dto.getCurrentPhaseId() != null) {
            LifecyclePhase phase = _lifecyclePhaseRepository.findById(dto.getCurrentPhaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Phase not found: " + dto.getCurrentPhaseId()));
            entity.setCurrentPhase(phase);
        }

        List<GeographicRegion> regions = dto.getRegionIds().stream()
                .map(id -> _regionRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Region not found: " + id)))
                .collect(Collectors.toList());
        entity.setRegions(regions);

        List<UserType> userTypes = dto.getUserTypeIds().stream()
                .map(id -> _userTypeRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("UserType not found: " + id)))
                .collect(Collectors.toList());
        entity.setUserTypes(userTypes);

        PriceList saved = _repository.save(entity);

        if (dto.getItems() != null) {
            Set<Long> seenProductIds = new HashSet<>();

            for (PriceListItemDto itDto : dto.getItems()) {
                Long productId = itDto.getProductId();

                if (productId == null || !seenProductIds.add(productId)) continue;

                PriceListItem item = new PriceListItem();
                item.setPriceList(saved);
                item.setProductId(productId);
                item.setPrice(itDto.getPrice());

                saved.getItems().add(item);
            }
        }

        _repository.flush();
        saved = _repository.findById(saved.getId()).orElseThrow();

        return priceListDtoMapper.fromEntityToDto(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PriceListDto> findAll() {
        return _repository.findAllWithoutStatus(NotificationStatus.PENDING)
                .stream()
                .map(priceListDtoMapper::fromEntityToDto)
                .toList();
    }

    @Override
    @Transactional
    public PriceListDto update(Long id, PriceListDto dto) {
        var pl = _repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PriceList not found: " + id));

        var createdBy = "system";
        priceListChangeRequestService.createDraft(pl.getId(), dto, createdBy);

        return priceListDtoMapper.fromEntityToDto(pl);
    }

    @Override
    @Transactional(readOnly = true)
    public PriceListDto findById(Long id) {
        PriceList entity = _repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Price list not found"));
        return priceListDtoMapper.fromEntityToDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean exists = _repository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Price list not found");
        }
        _repository.deleteById(id);
    }
}
