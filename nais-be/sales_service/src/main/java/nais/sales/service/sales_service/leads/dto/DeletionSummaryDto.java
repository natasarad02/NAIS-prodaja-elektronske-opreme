package nais.sales.service.sales_service.leads.dto;

import lombok.Builder;

@Builder
public record DeletionSummaryDto(
        String report,
        Long deletedContactsCount
) {}
