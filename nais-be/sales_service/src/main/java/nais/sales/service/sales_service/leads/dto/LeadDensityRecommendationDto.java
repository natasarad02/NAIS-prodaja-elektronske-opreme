package nais.sales.service.sales_service.leads.dto;

import lombok.Builder;

@Builder
public record LeadDensityRecommendationDto(
        String accountName,
        Long totalContacts,
        Long totalLeads,
        Double averageLeadsPerContact,
        Long earlyStageLeadsCount,
        String recommendation
) {}
