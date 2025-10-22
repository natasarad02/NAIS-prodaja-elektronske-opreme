package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LeadRelationshipServiceImpl implements LeadRelationshipService {
    public final LeadService leadService;
    private final AccountService accountService;
    private final ContactService contactService;
    private final LeadLifecycleService leadLifecycleService;
    private final LeadStatusService leadStatusService;

    @Override
    public Lead setAccount(UUID leadId, UUID accountId) {
        Lead lead = leadService.checkExists(leadId);
        Account account = accountService.checkExists(accountId);
        lead.setAccount(account);
        return leadService.update(lead);
    }

    @Override
    public Lead removeAccount(UUID leadId, UUID accountId) {
        Lead lead = leadService.checkExists(leadId);
        lead.setAccount(null);
        return leadService.update(lead);
    }

    @Override
    public Lead setContact(UUID leadId, UUID contactId) {
        Lead lead = leadService.checkExists(leadId);
        Contact contact = contactService.checkExists(contactId);
        lead.setContact(contact);
        return leadService.update(lead);
    }

    @Override
    public Lead removeContact(UUID leadId, UUID contactId) {
        Lead lead = leadService.checkExists(leadId);
        lead.setContact(null);
        return leadService.update(lead);
    }

    @Override
    public Lead setLifecycle(UUID leadId, UUID lifecycleId) {
        Lead lead = leadService.checkExists(leadId);
        LeadLifecycle leadLifecycle = leadLifecycleService.checkExists(lifecycleId);
        LeadStatus leadStatus = leadStatusService.checkExists(leadId);
        lead.setLeadLifecycle(leadLifecycle);
        lead.setLeadStatus(leadStatus);
        return leadService.update(lead);
    }

    @Override
    public Lead removeLifecycle(UUID leadId, UUID lifecycleId) {
        Lead lead = leadService.checkExists(leadId);
        lead.setLeadLifecycle(null);
        lead.setLeadStatus(null);
        return leadService.update(lead);
    }

    @Override
    public Lead setStatus(UUID leadId, UUID statusId) {
        Lead lead = leadService.checkExists(leadId);
        LeadStatus leadStatus = leadStatusService.checkExists(statusId);
        lead.setLeadStatus(leadStatus);
        return leadService.update(lead);
    }

    @Override
    public Lead removeStatus(UUID leadId, UUID statusId) {
        Lead lead = leadService.checkExists(leadId);
        lead.setLeadStatus(null);
        return leadService.update(lead);
    }
}
