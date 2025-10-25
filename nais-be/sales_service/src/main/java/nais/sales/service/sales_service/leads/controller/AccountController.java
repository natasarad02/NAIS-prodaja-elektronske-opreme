package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.AccountDto;
import nais.sales.service.sales_service.leads.mapper.AccountMapper;
import nais.sales.service.sales_service.leads.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/more-then-n-leads/{number}")
    public ResponseEntity<List<AccountDto>> getMoreThenNLeads(@PathVariable("number") Integer number) {
        return ResponseEntity.ok(accountService.findByLeadsNumber(number));
    }

    @GetMapping("")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll().stream()
                .map(AccountMapper::toDto)
                .toList());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("accountId") UUID accountId) {
        return ResponseEntity.ok(AccountMapper.toDto(accountService.checkExists(accountId)));
    }

    @PostMapping("")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(AccountMapper.toDto(accountService.create(AccountMapper.toEntity(accountDto))));
    }

    @PatchMapping("")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(AccountMapper.toDto(accountService.update(AccountMapper.toEntity(accountDto))));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable("accountId") UUID accountId) {
        accountService.deleteById(accountId);
        return ResponseEntity.ok().build();
    }
}
