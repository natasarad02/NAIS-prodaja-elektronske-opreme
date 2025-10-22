package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.model.LeadStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface LeadStatusService {
    LeadStatus create(LeadStatus status);
    Optional<LeadStatus> findById(UUID id);
    List<LeadStatus> findAll();
    LeadStatus update(LeadStatus status);
    boolean deleteById(UUID id);
    LeadStatus checkExists(UUID id);
    List<LeadStatus> findByLeadId(UUID leadId);
    void deleteIsNextRelationship(UUID statusId);

    LeadStatus findFirstForLifecycle(UUID lifecycleId);
}
