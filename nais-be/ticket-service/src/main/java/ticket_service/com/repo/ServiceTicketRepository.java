package ticket_service.com.repo;

import ticket_service.com.model.ServiceTicket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceTicketRepository extends ElasticsearchRepository<ServiceTicket, String> {
    
    List<ServiceTicket> findByCurrentStateId(String stateId);
    
    List<ServiceTicket> findByCurrentStateName(String stateName);
    
    List<ServiceTicket> findByServiceType(String serviceType);
    
    List<ServiceTicket> findByCustomerId(String customerId);
    
    List<ServiceTicket> findByAssignedTo(String assignedTo);
    
    List<ServiceTicket> findByIsResolved(Boolean isResolved);
    
    List<ServiceTicket> findByPriority(Integer priority);
    
    List<ServiceTicket> findByDeviceType(String deviceType);
    
    List<ServiceTicket> findBySerialNumber(String serialNumber);
    
    List<ServiceTicket> findByIsResolvedAndCurrentStateId(Boolean isResolved, String stateId);
    
    List<ServiceTicket> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<ServiceTicket> findByResolvedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<ServiceTicket> findByCustomerRatingGreaterThanEqual(Integer rating);
    
    List<ServiceTicket> findByPriorityAndCurrentStateId(Integer priority, String stateId);
}
