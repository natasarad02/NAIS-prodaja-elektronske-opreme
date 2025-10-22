package nais.sales.service.sales_service.leads.dto;

import lombok.Builder;

@Builder
public record AccountRankingDto(
        String accountName,
        Long totalContacts,
        Long statusLeadCount
) {}
