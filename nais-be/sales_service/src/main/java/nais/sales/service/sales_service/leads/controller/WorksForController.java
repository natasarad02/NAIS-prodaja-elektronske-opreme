package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.service.WorksForService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/works-for")
@AllArgsConstructor
public class WorksForController {
    private final WorksForService worksForService;

    @PostMapping("/add/{accountId}/{contactId}")
    public ResponseEntity<String> add(@PathVariable UUID  accountId, @PathVariable UUID contactId) {
        if(worksForService.AddContactToAccount(accountId, contactId)) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/remove/{contactId}")
    public ResponseEntity<String> remove(@PathVariable UUID contactId) {
        if(worksForService.RemoveContactFromAccount(contactId)) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().build();
    }
}
