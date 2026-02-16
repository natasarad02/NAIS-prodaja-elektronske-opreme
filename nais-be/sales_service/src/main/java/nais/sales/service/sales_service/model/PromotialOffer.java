package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "promotial_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@DynamicInsert
@DynamicUpdate
public class PromotialOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotial_offer_seq")
    @SequenceGenerator(name = "promotial_offer_seq", sequenceName = "promotial_offer_seq_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefit_id", nullable = false)
    private SpecialBenefit benefit;

}
