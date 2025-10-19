package nais.sales.service.sales_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "offer_order_match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferOrderMatch {
    @EmbeddedId
    private OfferOrderMatchId id;

    @Column(name = "matched_on", nullable = false)
    private Instant matchedOn = Instant.now();
}