package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.Contact;
import nais.sales.service.sales_service.leads.model.Lead;
import nais.sales.service.sales_service.leads.repository.ContactRepository;
import nais.sales.service.sales_service.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HasContactServiceImpl implements HasContactService{
    private final LeadRepository leadRepository;
    private final ContactRepository contactRepository;

    @Override
    public boolean addContactToLead(UUID leadId, UUID contactId) {
        Lead lead = leadRepository.findById(leadId).orElse(null);
        Contact contact = contactRepository.findById(contactId).orElse(null);
        if(lead==null || contact==null){
            return false;
        }
        lead.setContact(contact);
        contact.getLeads().add(lead);
        leadRepository.save(lead);
        contactRepository.save(contact);
        return true;
    }

    @Override
    public boolean removeContactFromLead(UUID leadId) {
        Lead lead = leadRepository.findById(leadId).orElse(null);
        if(lead==null){
            return false;
        }
        lead.setContact(null);
        leadRepository.save(lead);
        return true;
    }
}
