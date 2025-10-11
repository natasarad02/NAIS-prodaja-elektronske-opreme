package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.PriceListDto;
import nais.sales.service.sales_service.service.PriceListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/price_list")
public class PriceListController {
    private PriceListService service;

    public PriceListController(PriceListService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PriceListDto>> getPriceList() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<PriceListDto> createPriceList(@RequestBody PriceListDto priceListDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(priceListDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceListDto> updatePriceList(@PathVariable Long id, @RequestBody PriceListDto priceListDto) {
        return ResponseEntity.ok(service.update(id, priceListDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceListDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
