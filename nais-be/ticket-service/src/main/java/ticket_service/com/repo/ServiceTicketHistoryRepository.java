package ticket_service.com.repo;

import ticket_service.com.model.ServiceTicketHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceTicketHistoryRepository extends ElasticsearchRepository<ServiceTicketHistory, String> {
    
    List<ServiceTicketHistory> findByTicketId(String ticketId);
    
    List<ServiceTicketHistory> findByTicketIdOrderByChangedAtAsc(String ticketId);
    
    List<ServiceTicketHistory> findByFromStateId(String fromStateId);
    
    List<ServiceTicketHistory> findByToStateId(String toStateId);
    
    List<ServiceTicketHistory> findByChangedBy(String changedBy);
    
    List<ServiceTicketHistory> findByIsAutomatedChange(Boolean isAutomatedChange);
    
    List<ServiceTicketHistory> findByChangeReason(String changeReason);
    
    List<ServiceTicketHistory> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<ServiceTicketHistory> findByTicketIdAndChangedAtBetween(String ticketId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<ServiceTicketHistory> findByDurationInPreviousStateGreaterThan(Long minutes);
    
    List<ServiceTicketHistory> findByFromStateIdOrderByDurationInPreviousStateDesc(String fromStateId);
    
    List<ServiceTicketHistory> findByFromStateIdAndToStateId(String fromStateId, String toStateId);
}
