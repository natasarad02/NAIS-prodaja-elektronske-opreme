package nais.sales.service.sales_service.dto;

import java.time.Instant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListEventDto {
    private String price_list_id;
    private String action;
    private Long   event;
    private Double discount;
    private Long   quantity;
    private String title;
    private Long   phaseId;
    private Instant event_time;
}