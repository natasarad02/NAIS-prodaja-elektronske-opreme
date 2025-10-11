package nais.sales.service.sales_service.model;

import jakarta.persistence.*;
import lombok.*;
import nais.sales.service.sales_service.enums.NotificationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_list_change_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plcr_seq")
    @SequenceGenerator(name = "plcr_seq", sequenceName = "plcr_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "price_list_id", nullable = false)
    private PriceList priceList;

    @Lob
    @Column(name = "proposed_payload", nullable = false)
    private String proposedPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by")
    private String createdBy;
}
