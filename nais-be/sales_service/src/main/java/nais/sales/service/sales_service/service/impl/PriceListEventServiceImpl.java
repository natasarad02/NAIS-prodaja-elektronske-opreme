package nais.sales.service.sales_service.service.impl;

import lombok.RequiredArgsConstructor;
import nais.sales.service.sales_service.model.PriceListEvent;
import nais.sales.service.sales_service.repository.PriceListEventRepository;
import nais.sales.service.sales_service.service.PriceListEventService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceListEventServiceImpl implements PriceListEventService {
    private final PriceListEventRepository repo;

    public void create(PriceListEvent e) { e.setAction(e.getAction()==null? "CREATE": e.getAction()); repo.save(e); }
    public void update(PriceListEvent e) { e.setAction(e.getAction()==null? "UPDATE": e.getAction()); repo.save(e); }
    public void deleteLogically(String priceListId) {
        var e = new PriceListEvent();
        e.setPrice_list_id(priceListId);
        e.setAction("DELETE");
        repo.save(e);
    }

    public List<PriceListEvent> timeline(String priceListId, Instant from, Instant to) {
        return repo.timeline(priceListId, from, to);
    }

    public void deletePhysically(String priceListId, Instant from, Instant to) {
        repo.deleteByPriceListId(priceListId, from, to);
    }
}
