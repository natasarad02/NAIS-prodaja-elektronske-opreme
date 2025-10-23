package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadDto;
import nais.sales.service.sales_service.leads.service.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/leads")
@AllArgsConstructor
public class LeadsController {
    private final LeadService leadService;

    @GetMapping("/for-status/{statusId}")
    public ResponseEntity<List<LeadDto>> getLeadsByStatus(@PathVariable("statusId") UUID statusId) {
        return ResponseEntity.ok(leadService.getWithStatus(statusId));
    }
}
