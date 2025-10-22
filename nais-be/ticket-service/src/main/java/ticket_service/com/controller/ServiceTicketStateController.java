package ticket_service.com.controller;

import ticket_service.com.model.ServiceTicketState;
import ticket_service.com.repo.ServiceTicketStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/states")
public class ServiceTicketStateController {

    @Autowired
    private ServiceTicketStateRepository stateRepository;

    @PostMapping
    public ResponseEntity<ServiceTicketState> createState(@RequestBody ServiceTicketState state) {
        ServiceTicketState savedState = stateRepository.save(state);
        return new ResponseEntity<>(savedState, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceTicketState>> getAllStates() {
        List<ServiceTicketState> states = (List<ServiceTicketState>) stateRepository.findAll();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTicketState> getStateById(@PathVariable String id) {
        Optional<ServiceTicketState> state = stateRepository.findById(id);
        return state.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{stateName}")
    public ResponseEntity<ServiceTicketState> getStateByName(@PathVariable String stateName) {
        Optional<ServiceTicketState> state = stateRepository.findByStateName(stateName);
        return state.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ServiceTicketState>> getActiveStates() {
        List<ServiceTicketState> states = stateRepository.findByIsActiveTrueOrderByOrderSequenceAsc();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/active/{isActive}")
    public ResponseEntity<List<ServiceTicketState>> getStatesByActiveStatus(@PathVariable Boolean isActive) {
        List<ServiceTicketState> states = stateRepository.findByIsActive(isActive);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/initial")
    public ResponseEntity<ServiceTicketState> getInitialState() {
        Optional<ServiceTicketState> state = stateRepository.findByIsInitialState(true);
        return state.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/final")
    public ResponseEntity<List<ServiceTicketState>> getFinalStates() {
        List<ServiceTicketState> states = stateRepository.findByIsFinalState(true);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceTicketState>> getStatesByCategory(@PathVariable String category) {
        List<ServiceTicketState> states = stateRepository.findByStateCategory(category);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/requires-customer-action/{requiresAction}")
    public ResponseEntity<List<ServiceTicketState>> getStatesRequiringCustomerAction(@PathVariable Boolean requiresAction) {
        List<ServiceTicketState> states = stateRepository.findByRequiresCustomerAction(requiresAction);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/sequence/{sequence}")
    public ResponseEntity<ServiceTicketState> getStateBySequence(@PathVariable Integer sequence) {
        Optional<ServiceTicketState> state = stateRepository.findByOrderSequence(sequence);
        return state.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTicketState> updateState(@PathVariable String id, @RequestBody ServiceTicketState state) {
        if (!stateRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        state.setId(id);
        ServiceTicketState updatedState = stateRepository.save(state);
        return ResponseEntity.ok(updatedState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable String id) {
        if (!stateRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        stateRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<ServiceTicketState> toggleActiveStatus(@PathVariable String id) {
        Optional<ServiceTicketState> stateOpt = stateRepository.findById(id);
        if (stateOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ServiceTicketState state = stateOpt.get();
        state.setIsActive(!state.getIsActive());
        ServiceTicketState updatedState = stateRepository.save(state);
        return ResponseEntity.ok(updatedState);
    }
}
