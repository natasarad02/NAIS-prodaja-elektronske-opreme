package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.model.PriceListEvent;

import java.time.Instant;
import java.util.List;

public interface PriceListEventService {
    void create(PriceListEvent e);
    void update(PriceListEvent e);
    void deleteLogically(String priceListId);
    List<PriceListEvent> timeline(String priceListId, Instant from, Instant to);
    void deletePhysically(String priceListId, Instant from, Instant to);
}