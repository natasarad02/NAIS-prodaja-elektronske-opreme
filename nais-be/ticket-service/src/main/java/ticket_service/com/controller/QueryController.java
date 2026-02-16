package ticket_service.com.controller;

import ticket_service.com.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/queries")
public class QueryController {

    @Autowired
    private QueryService queryService;

    /**
     * COMPLEX QUERY 1:
     * 
     * Example:
     * GET /api/queries/search?searchText=ekran&minPriority=3
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTicketsWithFilters(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Integer minPriority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        
        Map<String, Object> result = queryService.searchTicketsWithTextAndFilters(
            searchText, minPriority, fromDate, toDate
        );
        return ResponseEntity.ok(result);
    }

    /**
     * COMPLEX QUERY 2:
     * 
     * Example:
     * GET /api/queries/bottlenecks?minDurationMinutes=300
     */
    @GetMapping("/bottlenecks")
    public ResponseEntity<Map<String, Object>> analyzeBottlenecks(
            @RequestParam(required = false) Long minDurationMinutes,
            @RequestParam(required = false) String stateCategory) {
        
        Map<String, Object> result = queryService.analyzeBottlenecks(
            minDurationMinutes, stateCategory
        );
        return ResponseEntity.ok(result);
    }

    /**
     * COMPLEX QUERY 3:
     * 
     * Example:
     * GET /api/queries/satisfaction?minRating=4
     */
    @GetMapping("/satisfaction")
    public ResponseEntity<Map<String, Object>> analyzeCustomerSatisfaction(
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(required = false) Boolean onlyResolved) {
        
        Map<String, Object> result = queryService.analyzeCustomerSatisfaction(
            minRating, fromDate, toDate, onlyResolved
        );
        return ResponseEntity.ok(result);
    }

    /**
     * COMPLEX QUERY 4:
     * 
     * Example:
     * GET /api/queries/search-transitions?searchText=popravka&fromState=U_OBRADI&toState=ZAVRSENO
     */
    @GetMapping("/search-transitions")
    public ResponseEntity<Map<String, Object>> searchWithStateTransitions(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String fromState,
            @RequestParam(required = false) String toState) {
        
        Map<String, Object> result = queryService.searchWithStateTransitionAnalysis(
            searchText, fromState, toState
        );
        return ResponseEntity.ok(result);
    }
}
