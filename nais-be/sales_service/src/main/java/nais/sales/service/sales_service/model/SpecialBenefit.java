package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "special_benefits")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "benefit_type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_seq")
    @SequenceGenerator(name = "benefit_seq", sequenceName = "benefit_id_seq", allocationSize = 1, initialValue = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false, length = 60)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "price_list_id", nullable = false)
    private PriceList priceList;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "benefit_regions",
            joinColumns = @JoinColumn(name = "benefit_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<GeographicRegion> regions = new HashSet<>();

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "benefit_user_types",
            joinColumns = @JoinColumn(name = "benefit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_type_id"))
    private Set<UserType> userTypes = new HashSet<>();

}
