package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.dto.LeadLifecycleDto;

import java.util.UUID;

public interface ManageLeadLifecycleService {
    LeadLifecycleDto createLeadLifecycle(LeadLifecycleDto leadLifecycleDto);
    LeadLifecycleDto updateLeadStatuses(LeadLifecycleDto leadLifecycleDto);
    LeadLifecycleDto findById(UUID id);
}
