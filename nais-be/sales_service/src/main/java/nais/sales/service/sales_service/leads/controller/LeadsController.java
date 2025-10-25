package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadDto;
import nais.sales.service.sales_service.leads.mapper.LeadMapper;
import nais.sales.service.sales_service.leads.service.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public ResponseEntity<List<LeadDto>> getAllLeads() {
        return ResponseEntity.ok(leadService.findAll().stream()
                .map(LeadMapper::toDto)
                .toList());
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<LeadDto> getLeadById(@PathVariable("leadId") UUID leadId) {
        return ResponseEntity.ok(LeadMapper.toDto(leadService.checkExists(leadId)));
    }

    @PostMapping("")
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto) {
        return ResponseEntity.ok(LeadMapper.toDto(leadService.create(LeadMapper.toEntity(leadDto))));
    }

    @PatchMapping("")
    public ResponseEntity<LeadDto> updateLead(@RequestBody LeadDto leadDto) {
        return ResponseEntity.ok(LeadMapper.toDto(leadService.update(LeadMapper.toEntity(leadDto))));
    }

    @DeleteMapping("/{leadId}")
    public ResponseEntity<Void> deleteLead(@PathVariable("leadId") UUID leadId) {
        leadService.deleteById(leadId);
        return ResponseEntity.ok().build();
    }
}
