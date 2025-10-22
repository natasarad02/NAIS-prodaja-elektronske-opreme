package ticket_service.com.repo;

import ticket_service.com.model.ServiceTicketHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceTicketHistoryRepository extends ElasticsearchRepository<ServiceTicketHistory, String> {
    
    // Pronalaženje istorije za određeni nalog
    List<ServiceTicketHistory> findByTicketId(String ticketId);
    
    // Istorija sortirana po vremenu
    List<ServiceTicketHistory> findByTicketIdOrderByChangedAtAsc(String ticketId);
    
    // Pronalaženje po prethodnom stanju
    List<ServiceTicketHistory> findByFromStateId(String fromStateId);
    
    // Pronalaženje po novom stanju
    List<ServiceTicketHistory> findByToStateId(String toStateId);
    
    // Pronalaženje po korisniku koji je izvršio promenu
    List<ServiceTicketHistory> findByChangedBy(String changedBy);
    
    // Pronalaženje automatskih promena
    List<ServiceTicketHistory> findByIsAutomatedChange(Boolean isAutomatedChange);
    
    // Pronalaženje po razlogu promene
    List<ServiceTicketHistory> findByChangeReason(String changeReason);
    
    // Pronalaženje promena u vremenskom opsegu
    List<ServiceTicketHistory> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Pronalaženje promena za nalog u vremenskom opsegu
    List<ServiceTicketHistory> findByTicketIdAndChangedAtBetween(String ticketId, LocalDateTime startDate, LocalDateTime endDate);
    
    // Pronalaženje promena sa dužim trajanjem od određenog broja minuta
    List<ServiceTicketHistory> findByDurationInPreviousStateGreaterThan(Long minutes);
    
    // Analize uskih grla - stanja sa najdužim trajanjem
    List<ServiceTicketHistory> findByFromStateIdOrderByDurationInPreviousStateDesc(String fromStateId);
    
    // Istorija za specifičnu tranziciju između stanja
    List<ServiceTicketHistory> findByFromStateIdAndToStateId(String fromStateId, String toStateId);
}
