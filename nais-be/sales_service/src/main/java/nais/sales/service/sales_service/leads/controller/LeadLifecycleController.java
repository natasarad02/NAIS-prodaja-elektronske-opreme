package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadLifecycleDto;
import nais.sales.service.sales_service.leads.mapper.LeadLifecycleMapper;
import nais.sales.service.sales_service.leads.service.LeadLifecycleService;
import nais.sales.service.sales_service.leads.service.ManageLeadLifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/lead-lifecycles")
@AllArgsConstructor
public class LeadLifecycleController {
    private final ManageLeadLifecycleService manageLeadLifecycleService;
    private final LeadLifecycleService leadLifecycleService;

    @PostMapping("")
    public LeadLifecycleDto createLeadLifecycle(@RequestBody LeadLifecycleDto leadLifecycleDto) {
        return manageLeadLifecycleService.createLeadLifecycle(leadLifecycleDto);
    }

    @PatchMapping("")
    public LeadLifecycleDto updateLeadLifecycle(@RequestBody LeadLifecycleDto leadLifecycleDto) {
        return manageLeadLifecycleService.updateLeadStatuses(leadLifecycleDto);
    }

    @GetMapping("/{leadId}")
    public LeadLifecycleDto findById(@PathVariable UUID leadId) {
        return manageLeadLifecycleService.findById(leadId);
    }

    @GetMapping("")
    public ResponseEntity<List<LeadLifecycleDto>> getAllLeadLifecycles() {
        return ResponseEntity.ok(leadLifecycleService.findAll()
                .stream()
                .map(LeadLifecycleMapper::toDto)
                .toList());
    }
}
