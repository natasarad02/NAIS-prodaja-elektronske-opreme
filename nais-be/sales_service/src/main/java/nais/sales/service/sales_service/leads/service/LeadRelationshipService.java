package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.model.Lead;

import java.util.UUID;

public interface LeadRelationshipService {
    Lead setAccount(UUID leadId, UUID accountId);
    Lead removeAccount(UUID leadId, UUID accountId);
    Lead setContact(UUID leadId, UUID contactId);
    Lead removeContact(UUID leadId, UUID contactId);
    Lead setLifecycle(UUID leadId, UUID lifecycleId);
    Lead removeLifecycle(UUID leadId, UUID lifecycleId);
    Lead setStatus(UUID leadId, UUID statusId);
    Lead removeStatus(UUID leadId, UUID statusId);
}
