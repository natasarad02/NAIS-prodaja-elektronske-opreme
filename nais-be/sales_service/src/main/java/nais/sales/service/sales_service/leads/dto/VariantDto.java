package nais.sales.service.sales_service.leads.dto;

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
    private String variantCode;
    private String description;
    private String modelNumber;
    private Long productId;
}
