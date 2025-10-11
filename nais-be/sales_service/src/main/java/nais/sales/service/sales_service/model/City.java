package nais.sales.service.sales_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class City {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

//    @ManyToMany(mappedBy = "cities")
//    private Set<GeographicRegion> regions = new HashSet<>();
}
