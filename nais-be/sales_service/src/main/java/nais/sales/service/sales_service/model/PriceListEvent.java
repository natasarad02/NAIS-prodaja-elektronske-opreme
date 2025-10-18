package nais.sales.service.sales_service.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Measurement(name = "price_list_event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListEvent {

    @Column(tag = true)
    private String price_list_id;

    @Column(tag = true)
    private String action;

    @Column(name = "event")
    private Long event;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "title")
    private String title;

    @Column(name = "phase_id")
    private Long phaseId;

    @Column(name = "_time", timestamp = true)
    private Instant event_time;
}
