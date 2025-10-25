package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.Account;
import nais.sales.service.sales_service.leads.model.Contact;
import nais.sales.service.sales_service.leads.repository.AccountRepository;
import nais.sales.service.sales_service.leads.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WorksForServiceImpl implements WorksForService {
    private final AccountRepository accountRepository;
    private final ContactRepository contactRepository;

    @Override
    public boolean AddContactToAccount(UUID accountId, UUID contactId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        Contact contact = contactRepository.findById(contactId).orElse(null);
        if (account == null || contact == null) {
            return false;
        }

        contact.setAccount(account);
        account.getContacts().add(contact);
        accountRepository.save(account);
        contactRepository.save(contact);
        return true;
    }

    @Override
    public boolean RemoveContactFromAccount(UUID contactId) {
        Contact contact = contactRepository.findById(contactId).orElse(null);
        if (contact == null) {
            return false;
        }
        contact.setAccount(null);
        contactRepository.save(contact);
        return false;
    }
}
