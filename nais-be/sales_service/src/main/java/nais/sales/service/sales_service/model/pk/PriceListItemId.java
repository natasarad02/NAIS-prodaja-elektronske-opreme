package nais.sales.service.sales_service.model.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PriceListItemId implements Serializable {

    @Column(name = "price_list_id")
    private Long priceListId;

    @Column(name = "product_id")
    private Long productId;
}

