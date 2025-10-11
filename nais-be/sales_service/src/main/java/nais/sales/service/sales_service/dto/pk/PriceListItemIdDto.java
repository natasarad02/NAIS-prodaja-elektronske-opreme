package nais.sales.service.sales_service.dto.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItemIdDto implements Serializable {
    private Long priceListId;
    private Long productId;
}
