package nais.sales.service.sales_service.leads.dto;

import lombok.Builder;

@Builder
public record LeadUpdateResultDto(
        String id,
        String description,
        String oldStatus,
        String newStatus,
        String accountName
) {}
