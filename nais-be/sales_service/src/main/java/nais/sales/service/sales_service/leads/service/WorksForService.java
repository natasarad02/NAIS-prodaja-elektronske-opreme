package nais.sales.service.sales_service.leads.service;

import java.util.UUID;

public interface WorksForService {
    boolean AddContactToAccount(UUID accountId, UUID contactId);
    boolean RemoveContactFromAccount(UUID contactId);
}
