package nais.sales.service.sales_service.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "benefitType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TresholdPriceDto.class, name = "TRESHOLD"),
        @JsonSubTypes.Type(value = BuyGetDto.class, name = "BUY_GET"),
        @JsonSubTypes.Type(value = PercentageBenefitDto.class, name = "PERCENTAGE"),
})
public class SpecialBenefitDto {
    private Long id;
    private String name;
    private String benefitType;

    private Long priceListId;
}
