package nais.sales.service.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListDto {
    private long id;
    private String title;
    private BigDecimal discount;
    private Integer quantity;
    private LocalDate startDate;
    private LocalDate expireDate;

    private Long currentPhaseId;
    //private String currentPhaseName;

    private Set<Long> regionIds;
    //private Set<String> regionNames;

    private Set<Long> userTypeIds;
    //private Set<String> userTypeNames;

    private Set<PriceListItemDto> items;
}
