package nais.sales.service.sales_service.leads.mapper;

import nais.sales.service.sales_service.leads.dto.LeadStatusDto;
import nais.sales.service.sales_service.leads.model.LeadLifecycle;
import nais.sales.service.sales_service.leads.model.LeadStatus;

public class LeadStatusMapper {
    public static LeadStatus toEntity(LeadStatusDto dto) {
        return LeadStatus.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .lifecycle(dto.getLifecycleId() != null ?
                        LeadLifecycle.builder()
                                .id(dto.getLifecycleId())
                                .build() :
                        new LeadLifecycle())
                .previous(dto.getPrevId() != null ?
                        LeadStatus.builder()
                                .id(dto.getPrevId())
                                .build()
                        : new LeadStatus())
                .next(dto.getNextId() != null ?
                        LeadStatus.builder()
                                .id(dto.getNextId())
                                .build()
                        : new LeadStatus())
                .build();
    }

    public static LeadStatusDto toDto(LeadStatus entity) {
        return LeadStatusDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .lifecycleId(entity.getLifecycle() != null ?
                        entity.getLifecycle().getId() : null)
                .prevId(entity.getPrevious() != null ?
                        entity.getPrevious().getId() : null)
                .nextId(entity.getNext() != null ?
                        entity.getNext().getId() : null)
                .build();
    }
}
