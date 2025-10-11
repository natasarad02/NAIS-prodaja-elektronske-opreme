package nais.sales.service.sales_service.model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import nais.sales.service.sales_service.model.pk.PriceListItemId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@Table(
        name = "price_list_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"price_list_id", "product_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@DynamicInsert
@DynamicUpdate
public class PriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "price_list_id", nullable = false)
    private PriceList priceList;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
}

