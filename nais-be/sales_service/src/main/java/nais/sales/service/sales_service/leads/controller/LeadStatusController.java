package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadStatusDto;
import nais.sales.service.sales_service.leads.mapper.LeadStatusMapper;
import nais.sales.service.sales_service.leads.service.LeadStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{statusId}")
    public ResponseEntity<LeadStatusDto> getLeadStatus(@PathVariable UUID statusId) {
        return ResponseEntity.ok(LeadStatusMapper.toDto(leadStatusService.checkExists(statusId)));
    }

    @PostMapping("")
    public ResponseEntity<LeadStatusDto> createLeadStatus(@RequestBody LeadStatusDto leadStatusDto) {
        return ResponseEntity.ok(LeadStatusMapper.toDto(leadStatusService.create(LeadStatusMapper.toEntity(leadStatusDto))));
    }

    @PatchMapping("")
    public ResponseEntity<LeadStatusDto> updateLeadStatus(@RequestBody LeadStatusDto leadStatusDto) {
        return ResponseEntity.ok(LeadStatusMapper.toDto(leadStatusService.update(LeadStatusMapper.toEntity(leadStatusDto))));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deleteLeadStatus(@PathVariable UUID statusId) {
        leadStatusService.deleteById(statusId);
        return ResponseEntity.ok().build();
    }
}
