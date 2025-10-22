package nais.sales.service.sales_service.leads.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadLifecycleDto {
    private UUID id;
    private String name;
    private String description;
    private List<LeadStatusDto> leadStatuses = new ArrayList<>();
}
