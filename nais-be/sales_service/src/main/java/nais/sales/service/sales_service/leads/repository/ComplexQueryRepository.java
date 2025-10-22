package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.dto.*;

import java.util.List;

public interface ComplexQueryRepository {
    List<AccountRankingDto> getQualifiedLeadRanking(String statusName);
    List<LeadStatusSummaryDto> getOldLeadStatusSummary(String lifecycleName);
    List<LeadDensityRecommendationDto> getHighDensityAccounts(List<String> earlyStageNames);

    List<LeadUpdateResultDto> updateContactedLeadsToQualified(String fromStatus, String toStatus);
    DeletionSummaryDto deleteOldPartnerApprovedLeads(String lifecycleName, String statusName);
}
