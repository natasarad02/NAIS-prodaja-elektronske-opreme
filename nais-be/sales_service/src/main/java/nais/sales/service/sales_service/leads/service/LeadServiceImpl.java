package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.Lead;
import nais.sales.service.sales_service.leads.repository.LeadRepository;
import static nais.sales.service.sales_service.leads.logger.AppLogger.LOG;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LeadServiceImpl implements LeadService {
    private final LeadRepository leadRepository;

    @Override
    public Lead create(Lead lead) {
        return leadRepository.save(lead);
    }

    @Override
    public Optional<Lead> findById(UUID id) {
        return leadRepository.findById(id);
    }

    @Override
    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    @Override
    public Lead update(Lead lead) {
        return leadRepository.save(lead);
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            leadRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Failed to delete lead with id {}", id, e);
            return false;
        }
        return true;
    }

    @Override
    public Lead checkExists(UUID id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lead with id " + id + " does not exist"));
    }
}
