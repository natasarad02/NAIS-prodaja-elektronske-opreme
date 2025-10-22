package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadLifecycleDto;
import nais.sales.service.sales_service.leads.dto.LeadStatusDto;
import nais.sales.service.sales_service.leads.mapper.LeadLifecycleMapper;
import nais.sales.service.sales_service.leads.mapper.LeadStatusMapper;
import nais.sales.service.sales_service.leads.model.LeadLifecycle;
import nais.sales.service.sales_service.leads.model.LeadStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ManageLeadLifecycleServiceImpl implements ManageLeadLifecycleService {
    private final LeadLifecycleService leadLifecycleService;
    private final LeadStatusService leadStatusService;

    @Override
    @Transactional
    public LeadLifecycleDto createLeadLifecycle(LeadLifecycleDto leadLifecycleDto) {
        LeadLifecycle leadLifecycle = LeadLifecycleMapper.toEntity(leadLifecycleDto);
        leadLifecycle.setLeadStatuses(null);
        leadLifecycle = leadLifecycleService.create(leadLifecycle);

        List<LeadStatus> leadStatuses = new ArrayList<>();
        LeadStatus prev = null;
        for (LeadStatusDto leadStatusDto : leadLifecycleDto.getLeadStatuses()) {
            LeadStatus leadStatus = LeadStatusMapper.toEntity(leadStatusDto);
            leadStatus.setLifecycle(leadLifecycle);
            leadStatus.setPrevious(prev);
            leadStatus.setNext(null);
            leadStatus = leadStatusService.create(leadStatus);
            if(prev != null){
                prev.setNext(leadStatus);
            }

            leadStatuses.add(leadStatus);
            prev = leadStatus;
        }

        leadLifecycle.setLeadStatuses(leadStatuses);
        return LeadLifecycleMapper.toDto(leadLifecycleService.update(leadLifecycle));
    }

    @Override
    @Transactional
    public LeadLifecycleDto updateLeadStatuses(LeadLifecycleDto leadLifecycleDto) {
        Optional<LeadLifecycle> leadLifecycle = leadLifecycleService.findById(leadLifecycleDto.getId());

        if(leadLifecycle.isPresent()){
            LeadLifecycle leadLifecycleEntity = leadLifecycle.get();
            leadLifecycleEntity.setName(leadLifecycleDto.getName());
            leadLifecycleEntity.setDescription(leadLifecycleDto.getDescription());

            UUID prevId = null;
            LeadStatus prev = null;
            for(LeadStatusDto leadStatusDto : leadLifecycleDto.getLeadStatuses()){
                Optional<LeadStatus> leadStatus = leadLifecycleEntity.getLeadStatuses().stream()
                        .filter(leadStatus1 -> leadStatus1.getId().equals(leadStatusDto.getId()))
                        .findFirst();

                leadStatusDto.setPrevId(prevId);
                if(prevId != null){
                    prev = leadStatusService.checkExists(prevId);
                }

                if(leadStatus.isPresent()){
                    LeadStatus leadStatusDtoEntity = leadStatusService.checkExists(leadStatusDto.getId());
                    leadStatusService.deleteIsNextRelationship(leadStatusDtoEntity.getId());

                    leadStatusDtoEntity.setName(leadStatusDto.getName());
                    leadStatusDtoEntity.setDescription(leadStatusDto.getDescription());

                    leadStatusDtoEntity.setPrevious(prev);
                    leadStatusDtoEntity.setNext(null);
                    if(prev != null){
                        prev.setNext(leadStatusDtoEntity);
                    }
                    leadStatusDtoEntity = leadStatusService.update(leadStatusDtoEntity);

                    prev =  leadStatusDtoEntity;
                    prevId = leadStatusDtoEntity.getId();

                } else {
                    LeadStatus newLeadStatus = LeadStatusMapper.toEntity(leadStatusDto);
                    newLeadStatus.setPrevious(prev);
                    newLeadStatus.setNext(null);
                    newLeadStatus.setId(null);
                    newLeadStatus.setLifecycle(leadLifecycleEntity);

                    newLeadStatus = leadStatusService.create(newLeadStatus);
                    leadLifecycle.get().getLeadStatuses().add(newLeadStatus);

                    if(prev != null){
                        prev.setNext(newLeadStatus);
                    }
                    prevId = newLeadStatus.getId();
                }
            }
            leadLifecycleService.update(leadLifecycle.get());
            return findById(leadLifecycleEntity.getId());
        }
        return null;
    }

    @Override
    public LeadLifecycleDto findById(UUID id) {
        Optional<LeadLifecycle> leadLifecycle = leadLifecycleService.findById(id);

        if (leadLifecycle.isPresent()){
            LeadLifecycle leadLifecycleEntity = leadLifecycle.get();
            List<LeadStatus> ordered = leadStatusService.findByLeadId(leadLifecycleEntity.getId());
            leadLifecycleEntity.setLeadStatuses(ordered);
            return LeadLifecycleMapper.toDto(leadLifecycle.get());
        }

        return null;
    }
}
