package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.service.HasLifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/has-lifecycle")
@AllArgsConstructor
public class HasLifecycleController {
    private final HasLifecycleService hasLifecycleService;

    @PostMapping("/add/{leadId}/{lifecycleId}")
    public ResponseEntity<String> add(@PathVariable UUID leadId, @PathVariable UUID lifecycleId) {
        if(hasLifecycleService.addLifecycleToLead(leadId, lifecycleId)) {
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/remove/{leadId}")
    public ResponseEntity<String> remove(@PathVariable UUID leadId) {
        if(hasLifecycleService.removeLifecycleFromLead(leadId)) {
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.notFound().build();
    }
}
