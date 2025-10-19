package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.LifecyclePhaseDto;
import nais.sales.service.sales_service.service.LifecyclePhaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/lifecycle_phase")

public class LifecyclePhaseController {

    private final LifecyclePhaseService service;

    public LifecyclePhaseController(LifecyclePhaseService service) {
        this.service = service;
    }

    @PostMapping
    
    public ResponseEntity<LifecyclePhaseDto> create(@RequestBody LifecyclePhaseDto dto) {
        LifecyclePhaseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<LifecyclePhaseDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LifecyclePhaseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    
    public ResponseEntity<LifecyclePhaseDto> update(@PathVariable Long id,
                                                    @RequestBody LifecyclePhaseDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
