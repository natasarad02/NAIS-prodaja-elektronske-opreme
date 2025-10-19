package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.LifecyclePhaseDto;
import nais.sales.service.sales_service.model.LifecyclePhase;
import nais.sales.service.sales_service.repository.LifecyclePhaseRepository;
import nais.sales.service.sales_service.service.LifecyclePhaseService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LifecyclePhaseServiceImpl implements LifecyclePhaseService {
    private static final Logger log = LoggerFactory.getLogger(LifecyclePhaseServiceImpl.class);
    private final LifecyclePhaseRepository _repository;

    public LifecyclePhaseServiceImpl(LifecyclePhaseRepository repository) {
        this._repository = repository;
    }

    @Override
    @Transactional
    public LifecyclePhaseDto create(LifecyclePhaseDto dto) {
        var entity = new LifecyclePhase();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        Integer resolved = resolvePosition(dto.getPosition(), null);
        entity.setPosition(resolved);

        if (dto.getNextPhaseIds() != null && !dto.getNextPhaseIds().isEmpty()) {
            var next = _repository.findAllById(dto.getNextPhaseIds());
            if (next.size() != dto.getNextPhaseIds().size()) {
                var foundIds = next.stream().map(LifecyclePhase::getId).collect(Collectors.toSet());
                var missing = new HashSet<>(dto.getNextPhaseIds());
                missing.removeAll(foundIds);
                throw new IllegalArgumentException("Ne postoje faze sa ID: " + missing);
            }
            entity.setNextPhases(new HashSet<>(next));
        } else {
            entity.setNextPhases(new HashSet<>());
        }

        var saved = _repository.save(entity);
        return toDto(saved);
    }


    @Override
    public List<LifecyclePhaseDto> findAll() {

        List<LifecyclePhase> phases = _repository.findAll();
        List<LifecyclePhaseDto> phaseDtos = phases.stream().map(phase -> new LifecyclePhaseDto(
                phase.getId(),
                phase.getTitle(),
                phase.getPosition(),
                phase.getDescription(),
                phase.getNextPhases().stream().map(LifecyclePhase::getId).collect(Collectors.toSet())
        )).toList();


        return phaseDtos;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!_repository.existsById(id)) {
            throw new EntityNotFoundException("LifecyclePhase ne postoji: " + id);
        }
        _repository.deleteById(id);
    }

    @Override
    @Transactional
    public LifecyclePhaseDto update(Long id, LifecyclePhaseDto dto) {
        var entity = _repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LifecyclePhase ne postoji: " + id));

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        Integer resolved = resolvePosition(dto.getPosition(), id);
        entity.setPosition(resolved);

        Set<Long> requested = dto.getNextPhaseIds();
        if (requested == null || requested.isEmpty()) {
            entity.setNextPhases(new HashSet<>());
        } else {
            if (requested.contains(id)) {
                throw new IllegalArgumentException("Faza ne može da referencira samu sebe: " + id);
            }
            var next = _repository.findAllById(requested);
            if (next.size() != requested.size()) {
                var foundIds = next.stream().map(LifecyclePhase::getId).collect(Collectors.toSet());
                var missing = new HashSet<>(requested);
                missing.removeAll(foundIds);
                throw new IllegalArgumentException("Ne postoje faze sa ID: " + missing);
            }
            entity.setNextPhases(new HashSet<>(next));
        }

        var saved = _repository.save(entity);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LifecyclePhaseDto findById(Long id) {
        var entity = _repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LifecyclePhase ne postoji: " + id));
        return toDto(entity);
    }

    private LifecyclePhaseDto toDto(LifecyclePhase e) {
        var dto = new LifecyclePhaseDto();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setPosition(e.getPosition());
        dto.setDescription(e.getDescription());
        dto.setNextPhaseIds(
                e.getNextPhases() == null
                        ? Set.of()
                        : e.getNextPhases().stream().map(LifecyclePhase::getId).collect(Collectors.toSet())
        );
        return dto;
    }

    private int resolvePosition(Integer requested, Long selfIdIfAny) {
        List<Integer> positions = _repository.findAllPositionsAsc();

        if (selfIdIfAny != null) {
            _repository.findById(selfIdIfAny).ifPresent(e -> positions.remove(Integer.valueOf(e.getPosition())));
        }

        if (requested != null && requested > 0 && positions.stream().noneMatch(p -> p != null && p.equals(requested))) {
            return requested;
        }

        int candidate = 1;
        for (Integer p : positions) {
            if (p == null) continue;
            if (p < candidate) continue;
            if (p == candidate) {
                candidate++;
            } else if (p > candidate) {
                break;
            }
        }
        return candidate;
    }

}
