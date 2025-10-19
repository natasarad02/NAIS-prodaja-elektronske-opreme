package nais.sales.service.sales_service.service;

import nais.sales.service.sales_service.model.PriceListEvent;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface PriceListEventService {
    PriceListEvent create(PriceListEvent e);
    void update(PriceListEvent e);
    void deleteLogically(String priceListId);
    List<PriceListEvent> timeline(String priceListId, Instant from, Instant to);
    void deletePhysically(String priceListId, Instant from, Instant to);
    List<Map<String, Object>> getActionCountsReport();
    List<Map<String, Object>> getTop5ByAverageDiscountReport();
    List<Map<String, Object>> getDailyTrendForTop3Report();
    List<Map<String, Object>> getDiscountVsQuantityReport(String priceListId);
}