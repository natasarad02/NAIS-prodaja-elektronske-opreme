package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.model.Account;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AccountService {
    Account create(Account account);
    Optional<Account> findById(UUID id);
    List<Account> findAll();
    Account update(Account account);
    boolean deleteById(UUID id);
    Account checkExists(UUID id);
}
