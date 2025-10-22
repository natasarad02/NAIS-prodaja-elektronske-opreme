package nais.sales.service.sales_service.leads.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadStatusDto {
    private UUID id;
    private String name;
    private String description;
    private UUID prevId;
    private UUID nextId;
    private UUID lifecycleId;
}
