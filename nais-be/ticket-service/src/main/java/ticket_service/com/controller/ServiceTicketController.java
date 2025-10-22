package ticket_service.com.controller;

import ticket_service.com.model.ServiceTicket;
import ticket_service.com.repo.ServiceTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class ServiceTicketController {

    @Autowired
    private ServiceTicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<ServiceTicket> createTicket(@RequestBody ServiceTicket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setIsResolved(false);
        ServiceTicket savedTicket = ticketRepository.save(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceTicket>> getAllTickets() {
        List<ServiceTicket> tickets = (List<ServiceTicket>) ticketRepository.findAll();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTicket> getTicketById(@PathVariable String id) {
        Optional<ServiceTicket> ticket = ticketRepository.findById(id);
        return ticket.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTicket> updateTicket(@PathVariable String id, @RequestBody ServiceTicket ticket) {
        if (!ticketRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        ticket.setUpdatedAt(LocalDateTime.now());
        ServiceTicket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        if (!ticketRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ticketRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByState(@PathVariable String stateId) {
        List<ServiceTicket> tickets = ticketRepository.findByCurrentStateId(stateId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/state/name/{stateName}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByStateName(@PathVariable String stateName) {
        List<ServiceTicket> tickets = ticketRepository.findByCurrentStateName(stateName);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/service-type/{serviceType}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByServiceType(@PathVariable String serviceType) {
        List<ServiceTicket> tickets = ticketRepository.findByServiceType(serviceType);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByCustomer(@PathVariable String customerId) {
        List<ServiceTicket> tickets = ticketRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/assigned/{assignedTo}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByAssignedTo(@PathVariable String assignedTo) {
        List<ServiceTicket> tickets = ticketRepository.findByAssignedTo(assignedTo);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/resolved/{isResolved}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByResolutionStatus(@PathVariable Boolean isResolved) {
        List<ServiceTicket> tickets = ticketRepository.findByIsResolved(isResolved);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByPriority(@PathVariable Integer priority) {
        List<ServiceTicket> tickets = ticketRepository.findByPriority(priority);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/device-type/{deviceType}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByDeviceType(@PathVariable String deviceType) {
        List<ServiceTicket> tickets = ticketRepository.findByDeviceType(deviceType);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/serial/{serialNumber}")
    public ResponseEntity<List<ServiceTicket>> getTicketsBySerialNumber(@PathVariable String serialNumber) {
        List<ServiceTicket> tickets = ticketRepository.findBySerialNumber(serialNumber);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/created-between")
    public ResponseEntity<List<ServiceTicket>> getTicketsCreatedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ServiceTicket> tickets = ticketRepository.findByCreatedAtBetween(startDate, endDate);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/resolved-between")
    public ResponseEntity<List<ServiceTicket>> getTicketsResolvedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ServiceTicket> tickets = ticketRepository.findByResolvedAtBetween(startDate, endDate);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByMinimumRating(@PathVariable Integer rating) {
        List<ServiceTicket> tickets = ticketRepository.findByCustomerRatingGreaterThanEqual(rating);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/priority/{priority}/state/{stateId}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByPriorityAndState(
            @PathVariable Integer priority,
            @PathVariable String stateId) {
        List<ServiceTicket> tickets = ticketRepository.findByPriorityAndCurrentStateId(priority, stateId);
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<ServiceTicket> resolveTicket(@PathVariable String id) {
        Optional<ServiceTicket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ServiceTicket ticket = ticketOpt.get();
        ticket.setIsResolved(true);
        ticket.setResolvedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ServiceTicket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @PatchMapping("/{id}/feedback")
    public ResponseEntity<ServiceTicket> addFeedback(
            @PathVariable String id,
            @RequestParam String feedback,
            @RequestParam Integer rating) {
        Optional<ServiceTicket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ServiceTicket ticket = ticketOpt.get();
        ticket.setCustomerFeedback(feedback);
        ticket.setCustomerRating(rating);
        ticket.setUpdatedAt(LocalDateTime.now());
        ServiceTicket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }
}
