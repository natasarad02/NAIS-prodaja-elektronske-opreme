package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.model.PriceListChangeRequest;
import nais.sales.service.sales_service.service.PriceListChangeRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-list-change-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PriceListChangeRequestController {

    private final PriceListChangeRequestService service;

    @GetMapping("/pending")
    
    public List<PriceListChangeRequest> pending() {
        return service.findAllPending();
    }

    @PostMapping("/{id}/approve")
    
    public void approve(@PathVariable Long id) {
        service.approve(id);
    }

    @GetMapping("/{id}")
    
    public PriceListChangeRequest getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/{id}/reject")
    
    public void reject(@PathVariable Long id) {
        service.reject(id);
    }
}
