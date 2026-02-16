package nais.sales.service.sales_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Country {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

//    @ManyToMany(mappedBy = "countries")
//    private Set<GeographicRegion> regions = new HashSet<>();
}
