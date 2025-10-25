package nais.sales.service.sales_service.leads.service;

import java.util.UUID;

public interface HasLifecycleService {
    boolean addLifecycleToLead(UUID leadId, UUID lifecycleId);
    boolean removeLifecycleFromLead(UUID leadId);
}
