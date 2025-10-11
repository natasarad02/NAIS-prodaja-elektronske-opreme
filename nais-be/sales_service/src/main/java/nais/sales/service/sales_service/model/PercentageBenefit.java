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
@Table(name = "percentage_benefits")
@DiscriminatorValue("PERCENTAGE")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PercentageBenefit extends SpecialBenefit {

    @Column(name = "percentage", nullable = false)
    private BigDecimal Percentage;

    @ElementCollection
    @CollectionTable(
            name = "benefit_products",
            joinColumns = @JoinColumn(name = "benefit_id")
    )
    @Column(name = "product_id")
    private Set<Long> products = new HashSet<>();


}
