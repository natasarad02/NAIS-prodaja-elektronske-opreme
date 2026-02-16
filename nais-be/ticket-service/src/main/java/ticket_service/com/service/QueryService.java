package ticket_service.com.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import ticket_service.com.model.ServiceTicket;
import ticket_service.com.model.ServiceTicketHistory;
import ticket_service.com.repo.ServiceTicketRepository;
import ticket_service.com.repo.ServiceTicketHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueryService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * COMPLEX QUERY 1:
     * 
     * Pretražuje tikete po tekstu (description ili title), filtrira po prioritetu i datumu,
     * sortira po relevantnosti i datumu, i agregira po tipu servisa.
     * 
     */
    public Map<String, Object> searchTicketsWithTextAndFilters(
            String searchText,
            Integer minPriority,
            LocalDateTime fromDate,
            LocalDateTime toDate) {

        List<Query> mustQueries = new ArrayList<>();

        // Full-text search across title and description
        if (searchText != null && !searchText.isEmpty()) {
            Query multiMatchQuery = Query.of(q -> q
                .multiMatch(m -> m
                    .query(searchText)
                    .fields("title", "description", "customerFeedback")
                    .fuzziness("AUTO")
                )
            );
            mustQueries.add(multiMatchQuery);
        }

        // Filter by minimum priority
        if (minPriority != null) {
            Query priorityQuery = Query.of(q -> q
                .range(RangeQuery.of(r -> r
                    .number(n -> n
                        .field("priority")
                        .gte(minPriority.doubleValue())
                    )
                ))
            );
            mustQueries.add(priorityQuery);
        }

        // Filter by date range
        if (fromDate != null && toDate != null) {
            Query dateRangeQuery = Query.of(q -> q
                .range(RangeQuery.of(r -> r
                    .date(d -> d
                        .field("createdAt")
                        .gte(fromDate.format(formatter))
                        .lte(toDate.format(formatter))
                    )
                ))
            );
            mustQueries.add(dateRangeQuery);
        }

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(mustQueries));

        NativeQuery query = new NativeQueryBuilder()
            .withQuery(Query.of(q -> q.bool(boolQuery)))
            .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
            .withSort(s -> s.field(f -> f.field("createdAt").order(SortOrder.Desc)))
            .withMaxResults(100)
            .build();

        SearchHits<ServiceTicket> searchHits = elasticsearchOperations.search(
            query,
            ServiceTicket.class,
            IndexCoordinates.of("service_tickets")
        );

        List<ServiceTicket> tickets = searchHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());

        // Manual aggregation by service type (since we can't easily get aggregations from SearchHits)
        Map<String, Long> serviceTypeCount = tickets.stream()
            .collect(Collectors.groupingBy(
                ServiceTicket::getServiceType,
                Collectors.counting()
            ));

        Map<String, Object> result = new HashMap<>();
        result.put("totalHits", searchHits.getTotalHits());
        result.put("tickets", tickets);
        result.put("aggregations", Map.of("byServiceType", serviceTypeCount));

        return result;
    }

    /**
     * COMPLEX QUERY 2: 
     * 
     * Analizira uska grla u procesu - pronalazi stanja gde tiketi provode najviše vremena,
     * filtrira po minimalnom trajanju, sortira po prosečnom trajanju, i agregira statistike.
     * 
     */
    public Map<String, Object> analyzeBottlenecks(
            Long minDurationMinutes,
            String stateCategory) {

        List<Query> mustQueries = new ArrayList<>();

        // Filter by minimum duration
        if (minDurationMinutes != null) {
            Query durationQuery = Query.of(q -> q
                .range(RangeQuery.of(r -> r
                    .number(n -> n
                        .field("durationInPreviousState")
                        .gte(minDurationMinutes.doubleValue())
                    )
                ))
            );
            mustQueries.add(durationQuery);
        }

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(mustQueries));

        NativeQuery query = new NativeQueryBuilder()
            .withQuery(Query.of(q -> q.bool(boolQuery)))
            .withSort(s -> s.field(f -> f
                .field("durationInPreviousState")
                .order(SortOrder.Desc)))
            .withMaxResults(1000)
            .build();

        SearchHits<ServiceTicketHistory> searchHits = elasticsearchOperations.search(
            query,
            ServiceTicketHistory.class,
            IndexCoordinates.of("service_ticket_history")
        );

        List<ServiceTicketHistory> historyEntries = searchHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());

        // Aggregate statistics by state
        Map<String, Map<String, Object>> stateStats = historyEntries.stream()
            .collect(Collectors.groupingBy(
                ServiceTicketHistory::getFromStateName,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        Map<String, Object> stats = new HashMap<>();
                        stats.put("count", list.size());
                        stats.put("averageDuration", list.stream()
                            .mapToLong(ServiceTicketHistory::getDurationInPreviousState)
                            .average()
                            .orElse(0.0));
                        stats.put("maxDuration", list.stream()
                            .mapToLong(ServiceTicketHistory::getDurationInPreviousState)
                            .max()
                            .orElse(0L));
                        stats.put("minDuration", list.stream()
                            .mapToLong(ServiceTicketHistory::getDurationInPreviousState)
                            .min()
                            .orElse(0L));
                        return stats;
                    }
                )
            ));

        // Sort states by average duration (descending)
        List<Map.Entry<String, Map<String, Object>>> sortedStates = stateStats.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(
                (Double) e2.getValue().get("averageDuration"),
                (Double) e1.getValue().get("averageDuration")
            ))
            .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("totalEntries", searchHits.getTotalHits());
        result.put("bottlenecksByState", sortedStates);
        result.put("topBottlenecks", historyEntries.stream().limit(10).collect(Collectors.toList()));

        return result;
    }

    /**
     * COMPLEX QUERY 3:
     * 
     * Analizira zadovoljstvo korisnika kombinujući više faktora - ocenu, vreme razrešavanja,
     * tip servisa, i prioritet. Filtrira, sortira i agregira podatke.
     * 
     */
    public Map<String, Object> analyzeCustomerSatisfaction(
            Integer minRating,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Boolean onlyResolved) {

        List<Query> mustQueries = new ArrayList<>();

        // Filter only resolved tickets
        if (onlyResolved != null && onlyResolved) {
            Query resolvedQuery = Query.of(q -> q
                .term(t -> t
                    .field("isResolved")
                    .value(true)
                )
            );
            mustQueries.add(resolvedQuery);
        }

        // Filter by minimum rating
        if (minRating != null) {
            Query ratingQuery = Query.of(q -> q
                .range(RangeQuery.of(r -> r
                    .number(n -> n
                        .field("customerRating")
                        .gte(minRating.doubleValue())
                    )
                ))
            );
            mustQueries.add(ratingQuery);
        }

        // Filter tickets that have ratings (not null)
        Query hasRatingQuery = Query.of(q -> q
            .exists(e -> e.field("customerRating"))
        );
        mustQueries.add(hasRatingQuery);

        // Filter by resolved date range
        if (fromDate != null && toDate != null) {
            Query dateRangeQuery = Query.of(q -> q
                .range(RangeQuery.of(r -> r
                    .date(d -> d
                        .field("resolvedAt")
                        .gte(fromDate.format(formatter))
                        .lte(toDate.format(formatter))
                    )
                ))
            );
            mustQueries.add(dateRangeQuery);
        }

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(mustQueries));

        NativeQuery query = new NativeQueryBuilder()
            .withQuery(Query.of(q -> q.bool(boolQuery)))
            .withSort(s -> s.field(f -> f.field("customerRating").order(SortOrder.Desc)))
            .withSort(s -> s.field(f -> f.field("resolvedAt").order(SortOrder.Desc)))
            .withMaxResults(500)
            .build();

        SearchHits<ServiceTicket> searchHits = elasticsearchOperations.search(
            query,
            ServiceTicket.class,
            IndexCoordinates.of("service_tickets")
        );

        List<ServiceTicket> tickets = searchHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());

        // Aggregate by service type
        Map<String, Map<String, Object>> satisfactionByServiceType = tickets.stream()
            .collect(Collectors.groupingBy(
                ServiceTicket::getServiceType,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        Map<String, Object> stats = new HashMap<>();
                        stats.put("count", list.size());
                        stats.put("averageRating", list.stream()
                            .mapToInt(ServiceTicket::getCustomerRating)
                            .average()
                            .orElse(0.0));
                        stats.put("averageDuration", list.stream()
                            .mapToLong(ServiceTicket::getTotalDuration)
                            .average()
                            .orElse(0.0));
                        return stats;
                    }
                )
            ));

        // Aggregate by priority
        Map<Integer, Double> averageRatingByPriority = tickets.stream()
            .collect(Collectors.groupingBy(
                ServiceTicket::getPriority,
                Collectors.averagingInt(ServiceTicket::getCustomerRating)
            ));

        Map<String, Object> result = new HashMap<>();
        result.put("totalTickets", searchHits.getTotalHits());
        result.put("tickets", tickets);
        result.put("satisfactionByServiceType", satisfactionByServiceType);
        result.put("averageRatingByPriority", averageRatingByPriority);
        result.put("overallAverageRating", tickets.stream()
            .mapToInt(ServiceTicket::getCustomerRating)
            .average()
            .orElse(0.0));

        return result;
    }

    /**
     * COMPLEX QUERY 4:
     * 
     * Kompleksna pretraga koja kombinuje full-text search sa analizom prelaza između stanja.
     * Koristi se za pronalaženje tiketa sa specifičnim sadržajem i njihovim životnim ciklusom.
     * 
     */
    public Map<String, Object> searchWithStateTransitionAnalysis(
            String searchText,
            String fromState,
            String toState) {

        List<Query> mustQueries = new ArrayList<>();

        // Full-text search
        if (searchText != null && !searchText.isEmpty()) {
            Query multiMatchQuery = Query.of(q -> q
                .multiMatch(m -> m
                    .query(searchText)
                    .fields("title", "description", "internalNotes")
                )
            );
            mustQueries.add(multiMatchQuery);
        }

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(mustQueries));

        NativeQuery ticketQuery = new NativeQueryBuilder()
            .withQuery(Query.of(q -> q.bool(boolQuery)))
            .withMaxResults(100)
            .build();

        SearchHits<ServiceTicket> ticketHits = elasticsearchOperations.search(
            ticketQuery,
            ServiceTicket.class,
            IndexCoordinates.of("service_tickets")
        );

        List<ServiceTicket> tickets = ticketHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());

        // For each ticket, get history with specific state transition
        List<Query> historyMustQueries = new ArrayList<>();
        
        if (fromState != null) {
            historyMustQueries.add(Query.of(q -> q
                .term(t -> t.field("fromStateName").value(fromState))
            ));
        }
        
        if (toState != null) {
            historyMustQueries.add(Query.of(q -> q
                .term(t -> t.field("toStateName").value(toState))
            ));
        }

        BoolQuery historyBoolQuery = BoolQuery.of(b -> b.must(historyMustQueries));

        NativeQuery historyQuery = new NativeQueryBuilder()
            .withQuery(Query.of(q -> q.bool(historyBoolQuery)))
            .withMaxResults(1000)
            .build();

        SearchHits<ServiceTicketHistory> historyHits = elasticsearchOperations.search(
            historyQuery,
            ServiceTicketHistory.class,
            IndexCoordinates.of("service_ticket_history")
        );

        List<ServiceTicketHistory> transitions = historyHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("matchingTickets", tickets.size());
        result.put("tickets", tickets);
        result.put("stateTransitions", transitions);
        result.put("transitionCount", transitions.size());

        return result;
    }
}
