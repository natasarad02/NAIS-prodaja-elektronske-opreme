package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.AdministratorNotifyDto;
import nais.sales.service.sales_service.service.AdministratorNotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/administrator-notifies")
public class AdministratorNotifyController {

    private final AdministratorNotifyService service;

    @GetMapping
    public List<AdministratorNotifyDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AdministratorNotifyDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<AdministratorNotifyDto> create(@RequestBody AdministratorNotifyDto dto) {
        var created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/administrator-notifies/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public AdministratorNotifyDto update(@PathVariable Long id, @RequestBody AdministratorNotifyDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
