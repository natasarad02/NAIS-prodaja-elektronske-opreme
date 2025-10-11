package nais.sales.service.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyGetDto extends SpecialBenefitDto {
    private List<Long> productsToBuyIds;
    private List<Long> productsToGetIds;
}
