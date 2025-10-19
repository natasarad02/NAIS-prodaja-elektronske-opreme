package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buy_gets")
@DiscriminatorValue("BUY_GET")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuyGet extends SpecialBenefit {

    @ElementCollection
    @CollectionTable(
            name = "buyx_products",
            joinColumns = @JoinColumn(name = "benefit_id")
    )
    @Column(name = "variant_id")
    private List<Long> productsToBuy = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "gety_products",
            joinColumns = @JoinColumn(name = "benefit_id")
    )
    @Column(name = "variant_id")
    private List<Long> productsToGet = new ArrayList<>();
}
