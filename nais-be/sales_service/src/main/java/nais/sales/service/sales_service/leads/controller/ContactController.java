package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.ContactDto;
import nais.sales.service.sales_service.leads.mapper.ContactMapper;
import nais.sales.service.sales_service.leads.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/controller")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("")
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        return ResponseEntity.ok(contactService.findAll().stream()
                .map(ContactMapper::toDto)
                .toList());
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable("contactId") UUID contactId) {
        return ResponseEntity.ok(ContactMapper.toDto(contactService.checkExists(contactId)));
    }

    @PostMapping("")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        return ResponseEntity.ok(ContactMapper.toDto(contactService.create(ContactMapper.toEntity(contactDto))));
    }

    @PatchMapping("")
    public ResponseEntity<ContactDto> updateContact(@RequestBody ContactDto contactDto) {
        return ResponseEntity.ok(ContactMapper.toDto(contactService.update(ContactMapper.toEntity(contactDto))));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable("contactId") UUID contactId) {
        contactService.deleteById(contactId);
        return ResponseEntity.ok().build();
    }
}
