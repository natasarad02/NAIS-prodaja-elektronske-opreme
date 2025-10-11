package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "treshold_prices")
@DiscriminatorValue("TRESHOLD_PRICE")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TresholdPrice extends SpecialBenefit {

    @Column(name = "treshold_price", nullable = false)
    private BigDecimal tresholdPrice;

    @Column(name = "discount_percentage", nullable = true)
    private BigDecimal discountPercentage;

    @Column(name = "discount", nullable = true)
    private BigDecimal discount;

    @ElementCollection
    @CollectionTable(
            name = "benefit_products",
            joinColumns = @JoinColumn(name = "benefit_id")
    )
    @Column(name = "product_id")
    private Set<Long> productIds = new HashSet<>();

}
