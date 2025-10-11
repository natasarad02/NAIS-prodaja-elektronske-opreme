package nais.sales.service.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LifecyclePhaseDto {
    private Long id;
    private String title;
    private int position;
    private String description;
    private Set<Long> nextPhaseIds;
}
