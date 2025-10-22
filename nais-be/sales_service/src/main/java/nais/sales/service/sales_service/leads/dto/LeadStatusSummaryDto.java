package nais.sales.service.sales_service.leads.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LeadStatusSummaryDto(
        String status,
        Long totalLeads,
        Long averageLeadAgeDays,
        LocalDateTime oldestLeadCreatedAt
) {}
