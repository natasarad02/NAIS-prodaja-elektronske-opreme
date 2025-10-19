package nais.sales.service.sales_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PriceList {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_list_seq")
    @SequenceGenerator(name = "price_list_seq", sequenceName = "price_list_seq_id", initialValue = 1, allocationSize = 1)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "current_phase_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIdentityReference(alwaysAsId = true)
    private LifecyclePhase currentPhase;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "price_list_regions",
            joinColumns = @JoinColumn(name = "price_list_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "region_id", nullable = false)
    )
    private List<GeographicRegion> regions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "price_list_user_types",
            joinColumns = @JoinColumn(name = "price_list_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_type_id", nullable = false)
    )
    private List<UserType> userTypes = new ArrayList<>();

    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceListItem> items = new ArrayList<>();


}
