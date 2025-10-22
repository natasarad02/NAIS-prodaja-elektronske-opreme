package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.LeadLifecycle;
import nais.sales.service.sales_service.leads.repository.LeadLifecycleRepository;
import static nais.sales.service.sales_service.leads.logger.AppLogger.LOG;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@AllArgsConstructor
public class LeadLifecycleServiceImpl implements LeadLifecycleService {
    private final LeadLifecycleRepository lifecycleRepository;

    @Override
    public LeadLifecycle create(LeadLifecycle lifecycle) {
        return lifecycleRepository.save(lifecycle);
    }

    @Override
    public Optional<LeadLifecycle> findById(UUID id) {
        return lifecycleRepository.findById(id);
    }

    @Override
    public List<LeadLifecycle> findAll() {
        return lifecycleRepository.findAll();
    }

    @Override
    public LeadLifecycle update(LeadLifecycle lifecycle) {
        return lifecycleRepository.save(lifecycle);
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            lifecycleRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Failed to delete lifecycle with id {}", id, e);
            return false;
        }
        return true;
    }

    @Override
    public LeadLifecycle checkExists(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lead with id " + id + " does not exist"));
    }
}
