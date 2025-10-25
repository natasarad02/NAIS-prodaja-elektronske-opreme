package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.Lead;
import nais.sales.service.sales_service.leads.model.LeadLifecycle;
import nais.sales.service.sales_service.leads.repository.LeadLifecycleRepository;
import nais.sales.service.sales_service.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HasLifecycleServiceImpl implements HasLifecycleService{
    private final LeadRepository leadRepository;
    private final LeadLifecycleRepository leadLifecycleRepository;

    @Override
    public boolean addLifecycleToLead(UUID leadId, UUID lifecycleId) {
        Lead lead = leadRepository.findById(leadId).orElse(null);
        LeadLifecycle lifecycle = leadLifecycleRepository.findById(lifecycleId).orElse(null);
        if(lead==null || lifecycle==null){
            return false;
        }
        lead.setLeadLifecycle(lifecycle);
        lifecycle.getLeads().add(lead);
        leadRepository.save(lead);
        leadLifecycleRepository.save(lifecycle);
        return true;
    }

    @Override
    public boolean removeLifecycleFromLead(UUID leadId) {
        Lead lead = leadRepository.findById(leadId).orElse(null);
        if(lead==null){
            return false;
        }
        lead.setLeadLifecycle(null);
        leadRepository.save(lead);
        return true;
    }
}
