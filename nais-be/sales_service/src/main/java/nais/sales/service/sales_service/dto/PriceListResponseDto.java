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
public class PriceListResponseDto {

    PriceListDto priceListDto;

    private String currentPhaseName;

    private Set<String> regionNames;

    private Set<String> userTypeNames;

    private Set<PriceListItemDto> items;
}