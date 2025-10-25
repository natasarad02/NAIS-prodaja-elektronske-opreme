package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.service.HasAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/has-account")
@AllArgsConstructor
public class HasAccountController {
    private final HasAccountService hasAccountService;

    @PostMapping("/add/{leadId}/{accountId}")
    public ResponseEntity<String> addAccoutnToLead(@PathVariable("leadId") UUID leadId, @PathVariable("accountId") UUID accountId) {
        if(hasAccountService.AddAccountToLead(leadId, accountId)){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/remove/{leadId}")
    public ResponseEntity<String> removeAccountFromLead(@PathVariable("leadId") UUID leadId) {
        if(hasAccountService.RemoveAccountFromLead(leadId)){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().build();
    }
}
