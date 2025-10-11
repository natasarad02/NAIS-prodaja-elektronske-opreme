package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.SpecialOfferDto;
import nais.sales.service.sales_service.service.SpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/special-offers")
public class SpecialOfferController {

    private final SpecialOfferService service;

    @PostMapping
    public ResponseEntity<SpecialOfferDto> create(@RequestBody SpecialOfferDto dto) {
        return ResponseEntity.ok(service.createSpecialOffer(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialOfferDto> update(
            @PathVariable Long id,
            @RequestBody SpecialOfferDto dto
    ) {
        dto.setId(id);
        return ResponseEntity.ok(service.updateSpecialOffer(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        SpecialOfferDto dto = new SpecialOfferDto();
        dto.setId(id);
        service.deleteSpecialOffer(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialOfferDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSpecialOfferById(id));
    }

    @GetMapping
    public ResponseEntity<List<SpecialOfferDto>> getAll() {
        return ResponseEntity.ok(service.getAllSpecialOffers());
    }
}
