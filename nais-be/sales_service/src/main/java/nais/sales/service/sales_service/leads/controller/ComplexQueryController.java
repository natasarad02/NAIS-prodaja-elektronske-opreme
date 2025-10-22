package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.*;
import nais.sales.service.sales_service.leads.service.ComplexQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/complex")
@AllArgsConstructor
public class ComplexQueryController {
    private final ComplexQueryService complexQueryService;

    @GetMapping("/account-ranking/{statusName}")
    public ResponseEntity<List<AccountRankingDto>> getAccountRanking(@PathVariable String statusName) {
        return ResponseEntity.ok(complexQueryService.getQualifiedLeadRanking(statusName));
    }

    @GetMapping("/lead-status-summery/{lifecycleName}")
    public ResponseEntity<List<LeadStatusSummaryDto>> getLeadStatusSummery(@PathVariable String lifecycleName) {
        return ResponseEntity.ok(complexQueryService.getOldLeadStatusSummary(lifecycleName));
    }

    @GetMapping("/lead-density-recommendation")
    public ResponseEntity<List<LeadDensityRecommendationDto>> getLeadDensityRecommendation() {
        return ResponseEntity.ok(complexQueryService.getHighDensityAccounts());
    }

    @GetMapping("/lead-update-result/{fromStatus}/{toStatus}")
    public ResponseEntity<List<LeadUpdateResultDto>> getLeadUpdateResult(
            @PathVariable String fromStatus, @PathVariable String toStatus
    ) {
        return ResponseEntity.ok(complexQueryService.updateContactedLeadsToQualified(fromStatus, toStatus));
    }

    @GetMapping("/delete-summery/{lifecycleId}")
    public ResponseEntity<DeletionSummaryDto> getDeleteSummery(@PathVariable UUID lifecycleId) {
        return ResponseEntity.ok(complexQueryService.deleteOldPartnerApprovedLeads(lifecycleId));
    }
}
