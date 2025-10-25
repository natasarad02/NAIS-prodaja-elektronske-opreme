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
    private String ticketId; 
    
    @Field(type = FieldType.Keyword)
    private String fromStateId;
    
    @Field(type = FieldType.Keyword)
    private String fromStateName;
    
    @Field(type = FieldType.Keyword)
    private String toStateId;
    
    @Field(type = FieldType.Keyword)
    private String toStateName;
    
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime changedAt;
    
    @Field(type = FieldType.Keyword)
    private String changedBy; // Ko je izvršio promenu (user ili sistem)
    
    @Field(type = FieldType.Long)
    private Long durationInPreviousState; // minuti
    
    @Field(type = FieldType.Keyword)
    private String changeReason; // npr. "PARTS_ARRIVED", "CUSTOMER_APPROVED"
    
    @Field(type = FieldType.Boolean)
    private Boolean isAutomatedChange;
}
