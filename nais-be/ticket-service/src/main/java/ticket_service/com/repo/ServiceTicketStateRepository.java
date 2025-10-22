package ticket_service.com.repo;

import ticket_service.com.model.ServiceTicketState;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTicketStateRepository extends ElasticsearchRepository<ServiceTicketState, String> {
    
    // Pronalaženje stanja po nazivu
    Optional<ServiceTicketState> findByStateName(String stateName);
    
    // Pronalaženje aktivnih stanja
    List<ServiceTicketState> findByIsActive(Boolean isActive);
    
    // Pronalaženje početnog stanja
    Optional<ServiceTicketState> findByIsInitialState(Boolean isInitialState);
    
    // Pronalaženje završnih stanja
    List<ServiceTicketState> findByIsFinalState(Boolean isFinalState);
    
    // Pronalaženje po kategoriji
    List<ServiceTicketState> findByStateCategory(String stateCategory);
    
    // Pronalaženje stanja koja zahtevaju akciju korisnika
    List<ServiceTicketState> findByRequiresCustomerAction(Boolean requiresCustomerAction);
    
    // Sve aktivne sortirane po redosledu
    List<ServiceTicketState> findByIsActiveTrueOrderByOrderSequenceAsc();
    
    // Pronalaženje po redosledu
    Optional<ServiceTicketState> findByOrderSequence(Integer orderSequence);
}
