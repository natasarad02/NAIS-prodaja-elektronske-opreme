package nais.sales.service.sales_service.leads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantDto {
    private Long id;
    @JsonProperty("variant_code")
    private String variantCode;
    private String description;
    @JsonProperty("model_number")
    private String modelNumber;
    @JsonProperty("product_id")
    private Long productId;
}
