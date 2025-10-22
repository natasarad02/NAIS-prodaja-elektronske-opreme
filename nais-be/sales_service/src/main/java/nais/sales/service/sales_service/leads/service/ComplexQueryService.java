package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.dto.*;

import java.util.List;
import java.util.UUID;

public interface ComplexQueryService {
    List<AccountRankingDto> getQualifiedLeadRanking(String statusName);
    List<LeadStatusSummaryDto> getOldLeadStatusSummary(String lifecycleName);
    List<LeadDensityRecommendationDto> getHighDensityAccounts();

    List<LeadUpdateResultDto> updateContactedLeadsToQualified(String fromStatus, String toStatus);
    DeletionSummaryDto deleteOldPartnerApprovedLeads(UUID lifecycleId);
}
