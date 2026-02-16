package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.SpecialBenefitDto;
import nais.sales.service.sales_service.service.SpecialBenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/benefits")
@RequiredArgsConstructor
public class SpecialBenefitController {
    private final SpecialBenefitService service;

    @PostMapping
    public ResponseEntity<SpecialBenefitDto> create(@RequestBody SpecialBenefitDto dto) {
        SpecialBenefitDto saved = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/benefits/" + saved.getId()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<SpecialBenefitDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
