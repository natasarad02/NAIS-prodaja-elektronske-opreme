package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;


@Entity
@Table(name = "special_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@DynamicInsert
@DynamicUpdate
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "special_offer_seq")
    @SequenceGenerator(name = "special_offer_seq", sequenceName = "special_offer_seq_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "payment_deadline")
    private LocalDate paymentDeadline;

    @Column(name = "payment_period")
    private Integer paymentPeriod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefit_id", nullable = false)
    private SpecialBenefit benefit;
}
