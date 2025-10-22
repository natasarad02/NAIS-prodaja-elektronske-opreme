package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.model.Contact;
import nais.sales.service.sales_service.leads.repository.ContactRepository;
import static nais.sales.service.sales_service.leads.logger.AppLogger.LOG;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public Contact create (Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> findById(UUID id) {
        return contactRepository.findById(id);
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact update(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            contactRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Failed to delete contact with id {}", id, e);
            return false;
        }
        return true;
    }

    @Override
    public Contact checkExists(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contact with id " + id + " does not exist"));
    }
}
