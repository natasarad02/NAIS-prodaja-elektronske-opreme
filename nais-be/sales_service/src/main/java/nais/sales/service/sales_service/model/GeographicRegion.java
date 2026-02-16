package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "regions")
@Getter
@Setter
@ToString(exclude = {"cities", "countries"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class GeographicRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_seq")
    @SequenceGenerator(name = "region_seq", sequenceName = "region_id_seq", allocationSize = 1, initialValue = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", length = 40, nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @ElementCollection
    @CollectionTable(
            name = "region_cities",
            joinColumns = @JoinColumn(name = "region_id")
    )
    @Column(name = "city_name", length = 100, nullable = false)
    private Set<String> cities = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "region_countries",
            joinColumns = @JoinColumn(name = "region_id")
    )
    @Column(name = "country_name", length = 100, nullable = false)
    private Set<String> countries = new HashSet<>();

//    @ManyToMany(mappedBy = "regions")
//    private Set<PriceList> priceLists = new HashSet<>();
}
