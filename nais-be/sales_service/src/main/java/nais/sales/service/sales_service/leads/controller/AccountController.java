package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.AccountDto;
import nais.sales.service.sales_service.leads.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/more-then-n-leads/{number}")
    public ResponseEntity<List<AccountDto>> getMoreThenNLeads(@PathVariable("number") Integer number) {
        return ResponseEntity.ok(accountService.findByLeadsNumber(number));
    }
}
