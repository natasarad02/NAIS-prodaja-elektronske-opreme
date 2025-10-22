package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.LeadStatus;
import nais.sales.service.sales_service.leads.repository.LeadStatusRepository;
import static nais.sales.service.sales_service.leads.logger.AppLogger.LOG;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class LeadStatusServiceImpl implements LeadStatusService {
    private final LeadStatusRepository statusRepository;

    @Override
    public LeadStatus create(LeadStatus status) {
        return statusRepository.save(status);
    }

    @Override
    public Optional<LeadStatus> findById(UUID id) {
        return statusRepository.findById(id);
    }

    @Override
    public List<LeadStatus> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public LeadStatus update(LeadStatus status) {
        return statusRepository.save(status);
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            statusRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Failed to delete lead status with id {}", id, e);
            return false;
        }
        return true;
    }

    @Override
    public LeadStatus checkExists(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lead status with id " + id + " does not exist"));
    }

    @Override
    public List<LeadStatus> findByLeadId(UUID leadId) {
        LeadStatus start = findFirstForLifecycle(leadId);

        List<LeadStatus> ordered = new ArrayList<>();
        LeadStatus current = start;
        while (current != null) {
            ordered.add(current);
            current = current.getNext();
        }
        return ordered;
    }

    @Override
    public void deleteIsNextRelationship(UUID statusId) {
        statusRepository.deletePreviousIsNext(statusId);
    }

    @Override
    public LeadStatus findFirstForLifecycle(UUID lifecycleId) {
        LeadStatus start = statusRepository
                .findFirstStatusInLifecycle(lifecycleId)
                .orElseThrow(() -> new NoSuchElementException("No start status found"));
       return checkExists(start.getId());
    }
}
