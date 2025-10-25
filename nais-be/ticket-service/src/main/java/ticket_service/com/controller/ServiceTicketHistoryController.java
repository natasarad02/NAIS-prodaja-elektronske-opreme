package ticket_service.com.controller;

import ticket_service.com.model.ServiceTicketHistory;
import ticket_service.com.repo.ServiceTicketHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/history")
public class ServiceTicketHistoryController {

    @Autowired
    private ServiceTicketHistoryRepository historyRepository;

    @PostMapping
    public ResponseEntity<ServiceTicketHistory> createHistoryEntry(@RequestBody ServiceTicketHistory history) {
        history.setChangedAt(LocalDateTime.now());
        ServiceTicketHistory savedHistory = historyRepository.save(history);
        return new ResponseEntity<>(savedHistory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceTicketHistory>> getAllHistory() {
        List<ServiceTicketHistory> history = new ArrayList<>();
        historyRepository.findAll().forEach(history::add);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTicketHistory> getHistoryById(@PathVariable String id) {
        Optional<ServiceTicketHistory> history = historyRepository.findById(id);
        return history.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByTicketId(@PathVariable String ticketId) {
        List<ServiceTicketHistory> history = historyRepository.findByTicketIdOrderByChangedAtAsc(ticketId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/from-state/{fromStateId}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByFromState(@PathVariable String fromStateId) {
        List<ServiceTicketHistory> history = historyRepository.findByFromStateId(fromStateId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/to-state/{toStateId}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByToState(@PathVariable String toStateId) {
        List<ServiceTicketHistory> history = historyRepository.findByToStateId(toStateId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/changed-by/{changedBy}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByChangedBy(@PathVariable String changedBy) {
        List<ServiceTicketHistory> history = historyRepository.findByChangedBy(changedBy);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/automated/{isAutomated}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByAutomation(@PathVariable Boolean isAutomated) {
        List<ServiceTicketHistory> history = historyRepository.findByIsAutomatedChange(isAutomated);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/reason/{reason}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByReason(@PathVariable String reason) {
        List<ServiceTicketHistory> history = historyRepository.findByChangeReason(reason);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ServiceTicketHistory> history = historyRepository.findByChangedAtBetween(startDate, endDate);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/ticket/{ticketId}/date-range")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByTicketAndDateRange(
            @PathVariable String ticketId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ServiceTicketHistory> history = historyRepository.findByTicketIdAndChangedAtBetween(ticketId, startDate, endDate);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/bottlenecks/{minutes}")
    public ResponseEntity<List<ServiceTicketHistory>> getBottlenecks(@PathVariable Long minutes) {
        List<ServiceTicketHistory> history = historyRepository.findByDurationInPreviousStateGreaterThan(minutes);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/bottlenecks/state/{fromStateId}")
    public ResponseEntity<List<ServiceTicketHistory>> getBottlenecksByState(@PathVariable String fromStateId) {
        List<ServiceTicketHistory> history = historyRepository.findByFromStateIdOrderByDurationInPreviousStateDesc(fromStateId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/transition/{fromStateId}/{toStateId}")
    public ResponseEntity<List<ServiceTicketHistory>> getHistoryByTransition(
            @PathVariable String fromStateId,
            @PathVariable String toStateId) {
        List<ServiceTicketHistory> history = historyRepository.findByFromStateIdAndToStateId(fromStateId, toStateId);
        return ResponseEntity.ok(history);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTicketHistory> updateHistory(@PathVariable String id, @RequestBody ServiceTicketHistory history) {
        if (!historyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        history.setId(id);
        ServiceTicketHistory updatedHistory = historyRepository.save(history);
        return ResponseEntity.ok(updatedHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable String id) {
        if (!historyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        historyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
