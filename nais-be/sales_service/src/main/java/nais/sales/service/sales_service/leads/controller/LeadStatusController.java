package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadStatusDto;
import nais.sales.service.sales_service.leads.mapper.LeadStatusMapper;
import nais.sales.service.sales_service.leads.service.LeadStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lead-statuses")
@AllArgsConstructor
public class LeadStatusController {
    private final LeadStatusService leadStatusService;

    @GetMapping("")
    public ResponseEntity<List<LeadStatusDto>> getAll() {
        return ResponseEntity.ok(leadStatusService.findAll()
                .stream()
                .map(LeadStatusMapper::toDto)
                .toList());
    }
}
