package ticket_service.com.repo;

import ticket_service.com.model.ServiceTicketState;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTicketStateRepository extends ElasticsearchRepository<ServiceTicketState, String> {
    
    Optional<ServiceTicketState> findByStateName(String stateName);
    
    List<ServiceTicketState> findByIsActive(Boolean isActive);
    
    Optional<ServiceTicketState> findByIsInitialState(Boolean isInitialState);
    
    List<ServiceTicketState> findByIsFinalState(Boolean isFinalState);
    
    List<ServiceTicketState> findByStateCategory(String stateCategory);
    
    List<ServiceTicketState> findByRequiresCustomerAction(Boolean requiresCustomerAction);
    
    List<ServiceTicketState> findByIsActiveTrueOrderByOrderSequenceAsc();
    
    Optional<ServiceTicketState> findByOrderSequence(Integer orderSequence);
}
