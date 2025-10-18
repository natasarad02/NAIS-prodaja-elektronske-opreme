package nais.sales.service.sales_service.controller;

import lombok.RequiredArgsConstructor;
import nais.sales.service.sales_service.model.PriceListEvent;
import nais.sales.service.sales_service.service.PriceListEventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/price-lists")
@RequiredArgsConstructor
public class PriceListEventController {

    private final PriceListEventService service;

    @PostMapping("/{id}/events/create")
    public void create(@PathVariable String id, @RequestBody PriceListEvent body) {
        body.setPrice_list_id(id);
        body.setAction("CREATE");
        service.create(body);
    }

    @PostMapping("/{id}/events/update")
    public void update(@PathVariable String id, @RequestBody PriceListEvent body) {
        body.setPrice_list_id(id);
        body.setAction("UPDATE");
        service.update(body);
    }

    @PostMapping("/{id}/events/delete-logical")
    public void deleteLogical(@PathVariable String id) {
        service.deleteLogically(id);
    }

    @DeleteMapping("/{id}/events")
    public void deletePhysical(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {
        service.deletePhysically(id, from, to);
    }

    @GetMapping("/{id}/events")
    public List<PriceListEvent> timeline(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {
        return service.timeline(id, from, to);
    }
}
