package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.UserTypeDto;
import nais.sales.service.sales_service.service.UserTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/user-type")
public class UserTypeController {

    private final UserTypeService service;

    public UserTypeController(UserTypeService service) {
        this.service = service;
    }

    @PostMapping
    
    public ResponseEntity<UserTypeDto> create(@RequestBody UserTypeDto dto) {
        UserTypeDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<UserTypeDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    
    public ResponseEntity<UserTypeDto> update(@PathVariable Long id,
                                              @RequestBody UserTypeDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
