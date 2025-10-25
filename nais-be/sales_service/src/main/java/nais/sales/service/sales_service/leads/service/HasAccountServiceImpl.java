package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.Account;
import nais.sales.service.sales_service.leads.model.Lead;
import nais.sales.service.sales_service.leads.repository.AccountRepository;
import nais.sales.service.sales_service.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HasAccountServiceImpl implements HasAccountService{
    private final AccountRepository accountRepository;
    private final LeadRepository leadRepository;

    @Override
    public boolean AddAccountToLead(UUID leadId, UUID accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        Lead lead = leadRepository.findById(leadId).orElse(null);
        if(account == null || lead == null) {
            return false;
        }
        lead.setAccount(account);
        leadRepository.save(lead);
        return true;
    }

    @Override
    public boolean RemoveAccountFromLead(UUID leadId) {
        Lead lead = leadRepository.findById(leadId).orElse(null);
        if(lead == null) {
            return false;
        }
        lead.setAccount(null);
        leadRepository.save(lead);
        return true;
    }
}
