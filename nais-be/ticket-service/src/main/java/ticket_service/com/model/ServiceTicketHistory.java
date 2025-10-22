package ticket_service.com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "service_ticket_history")
public class ServiceTicketHistory {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String ticketId; // Reference to ServiceTicket
    
    @Field(type = FieldType.Keyword)
    private String fromStateId; // Prethodno stanje
    
    @Field(type = FieldType.Keyword)
    private String fromStateName; // Denormalized for faster queries
    
    @Field(type = FieldType.Keyword)
    private String toStateId; // Novo stanje
    
    @Field(type = FieldType.Keyword)
    private String toStateName; // Denormalized for faster queries
    
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime changedAt; // Vreme promene stanja
    
    @Field(type = FieldType.Keyword)
    private String changedBy; // Ko je izvršio promenu (user ID ili sistem)
    
    @Field(type = FieldType.Long)
    private Long durationInPreviousState; // Trajanje u prethodnom stanju (minuti)
    
    @Field(type = FieldType.Keyword)
    private String changeReason; // Razlog promene (npr. "PARTS_ARRIVED", "CUSTOMER_APPROVED")
    
    @Field(type = FieldType.Boolean)
    private Boolean isAutomatedChange; // Da li je promena automatska ili manualna
}
