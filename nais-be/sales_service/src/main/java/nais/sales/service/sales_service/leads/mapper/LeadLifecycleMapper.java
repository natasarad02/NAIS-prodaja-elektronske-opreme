package nais.sales.service.sales_service.leads.mapper;

import nais.sales.service.sales_service.leads.dto.LeadLifecycleDto;
import nais.sales.service.sales_service.leads.model.LeadLifecycle;

import java.util.ArrayList;

public class LeadLifecycleMapper {
    public static LeadLifecycle toEntity(LeadLifecycleDto dto) {
        return LeadLifecycle.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .leadStatuses(dto.getLeadStatuses() != null
                        ? dto.getLeadStatuses().stream()
                        .map(LeadStatusMapper::toEntity)
                        .toList()
                        : new ArrayList<>())
                .build();
    }

    public static LeadLifecycleDto toDto(LeadLifecycle entity) {
        return LeadLifecycleDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .leadStatuses(entity.getLeadStatuses() != null
                        && !entity.getLeadStatuses().isEmpty()
                        ? entity.getLeadStatuses().stream()
                                .map(LeadStatusMapper::toDto)
                                .toList()
                        : new ArrayList<>())
                .build();
    }
}
