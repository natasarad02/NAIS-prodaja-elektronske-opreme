package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.AccountDto;
import nais.sales.service.sales_service.leads.model.Account;
import nais.sales.service.sales_service.leads.repository.AccountRepository;
import static nais.sales.service.sales_service.leads.logger.AppLogger.LOG;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            accountRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Failed to delete account with id {}", id, e);
            return false;
        }
        return true;
    }

    @Override
    public Account checkExists(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account with id " + id + " does not exist"));
    }

    @Override
    public List<AccountDto> findByLeadsNumber(int leadsNumber) {
        return accountRepository.findAccountsWithMoreThanNLeads(leadsNumber).stream()
                .map(a -> AccountDto.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .email(a.getEmail())
                        .phone(a.getPhone())
                        .build())
                .toList();
    }
}
