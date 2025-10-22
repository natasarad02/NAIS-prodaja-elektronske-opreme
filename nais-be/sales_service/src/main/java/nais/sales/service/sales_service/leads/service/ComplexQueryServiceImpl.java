package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.*;
import nais.sales.service.sales_service.leads.model.LeadLifecycle;
import nais.sales.service.sales_service.leads.model.LeadStatus;
import nais.sales.service.sales_service.leads.repository.ComplexQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ComplexQueryServiceImpl implements ComplexQueryService {
    private final ComplexQueryRepository complexQueryRepository;
    private final LeadStatusService leadStatusService;
    private final LeadLifecycleService leadLifecycleService;

    @Override
    public List<AccountRankingDto> getQualifiedLeadRanking(String statusName) {
        return complexQueryRepository.getQualifiedLeadRanking(statusName);
    }

    @Override
    public List<LeadStatusSummaryDto> getOldLeadStatusSummary(String lifecycleName) {
        return complexQueryRepository.getOldLeadStatusSummary(lifecycleName);
    }

    @Override
    public List<LeadDensityRecommendationDto> getHighDensityAccounts() {
        List<String> earlyStageNames = new ArrayList<>();
        List<LeadLifecycle> lifecycles = leadLifecycleService.findAll();
        for (LeadLifecycle lifecycle : lifecycles) {
            LeadStatus first = leadStatusService.findFirstForLifecycle(lifecycle.getId());
            LeadStatus next = first.getNext();
            earlyStageNames.add(first.getName());
            earlyStageNames.add(next.getName());
        }

        return complexQueryRepository.getHighDensityAccounts(earlyStageNames);
    }

    @Override
    @Transactional
    public List<LeadUpdateResultDto> updateContactedLeadsToQualified(String fromStatus, String toStatus) {
        return complexQueryRepository.updateContactedLeadsToQualified(fromStatus, toStatus);
    }

    @Override
    @Transactional
    public DeletionSummaryDto deleteOldPartnerApprovedLeads(UUID lifecycleId) {
        LeadStatus leadStatus = leadStatusService.findFirstForLifecycle(lifecycleId);
        String lifecycleName = leadStatus.getLifecycle().getName();

        String statusName = leadStatus.getName();
        while(leadStatus.getNext() != null) {
            leadStatus = leadStatus.getNext();
            statusName = leadStatus.getName();
        }

        return complexQueryRepository.deleteOldPartnerApprovedLeads(lifecycleName, statusName);
    }
}
