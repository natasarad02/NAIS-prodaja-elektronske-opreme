package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.service.HasContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/has-contact")
@AllArgsConstructor
public class HasContactController {
    private final HasContactService hasContactService;

    @PostMapping("/add/{leadId}/{contactId}")
    public ResponseEntity<String> addContactToLead(@PathVariable UUID leadId, @PathVariable UUID contactId) {
        if (hasContactService.addContactToLead(leadId, contactId)) {
            return ResponseEntity.ok("Contact has been added");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/remove/{leadId}")
    public ResponseEntity<String> removeContactFromLead(@PathVariable UUID leadId) {
        if (hasContactService.removeContactFromLead(leadId)) {
            return ResponseEntity.ok("Contact has been removed");
        }
        return ResponseEntity.notFound().build();
    }
}
