package nais.sales.service.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TresholdPriceDto extends SpecialBenefitDto {
    private BigDecimal tresholdPrice;
    private BigDecimal discountPercentage;
    private BigDecimal discount;
}
