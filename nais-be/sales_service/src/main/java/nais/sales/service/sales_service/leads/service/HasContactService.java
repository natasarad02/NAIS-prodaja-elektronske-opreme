package nais.sales.service.sales_service.leads.service;

import java.util.UUID;

public interface HasContactService {
    boolean addContactToLead(UUID leadId, UUID contactId);
    boolean removeContactFromLead(UUID leadId);
}
