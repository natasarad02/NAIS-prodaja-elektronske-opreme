package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.model.LeadLifecycle;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface LeadLifecycleService {
    LeadLifecycle create(LeadLifecycle lifecycle);
    Optional<LeadLifecycle> findById(UUID id);
    List<LeadLifecycle> findAll();
    LeadLifecycle update(LeadLifecycle lifecycle);
    boolean deleteById(UUID id);
    LeadLifecycle checkExists(UUID id);
}
