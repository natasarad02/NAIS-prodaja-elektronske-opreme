package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.GeographicRegionDto;
import nais.sales.service.sales_service.service.GeographicRegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")

public class GeographicRegionController {

    private final GeographicRegionService service;

    public GeographicRegionController(GeographicRegionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GeographicRegionDto> create(@RequestBody GeographicRegionDto dto) {
        GeographicRegionDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<GeographicRegionDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeographicRegionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeographicRegionDto> update(@PathVariable Long id,
                                                      @RequestBody GeographicRegionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
