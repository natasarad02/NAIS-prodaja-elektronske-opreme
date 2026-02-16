package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.*;
import nais.sales.service.sales_service.enums.NotificationStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "administrator_price_list_notify")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@DynamicInsert
@DynamicUpdate
public class AdministratorNotify {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "administrator_price_list_notify_seq")
    @SequenceGenerator(
            name = "administrator_price_list_notify_seq",
            sequenceName = "administrator_price_list_notify_seq_id",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private NotificationStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "price_list_id", nullable = false)
    private PriceList priceList;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
