package nais.sales.service.sales_service.leads.service;

import java.util.UUID;

public interface HasAccountService {
    boolean AddAccountToLead(UUID leadId, UUID accountId);
    boolean RemoveAccountFromLead(UUID leadId);
}
