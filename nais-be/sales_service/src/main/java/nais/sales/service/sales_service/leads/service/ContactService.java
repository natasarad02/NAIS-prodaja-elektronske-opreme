package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.model.Contact;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ContactService {
    Contact create(Contact contact);
    Optional<Contact> findById(UUID id);
    List<Contact> findAll();
    Contact update(Contact contact);
    boolean deleteById(UUID id);
    Contact checkExists(UUID id);
}
