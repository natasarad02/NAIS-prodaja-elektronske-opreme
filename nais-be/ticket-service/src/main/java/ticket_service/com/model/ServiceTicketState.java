package ticket_service.com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "service_ticket_states")
public class ServiceTicketState {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String stateName; // npr. "OTVORENO", "U_OBRADI", "ČEKA_DELOVE"
    
    @Field(type = FieldType.Text)
    private String stateDescription;
    
    @Field(type = FieldType.Integer)
    private Integer orderSequence;
    
    @Field(type = FieldType.Boolean)
    private Boolean isInitialState;
    
    @Field(type = FieldType.Boolean)
    private Boolean isFinalState;
    
    @Field(type = FieldType.Keyword)
    private String stateCategory; // Kategorija: "ACTIVE", "WAITING", "COMPLETED", "CANCELLED"
    
    @Field(type = FieldType.Boolean)
    private Boolean requiresCustomerAction;
    
    @Field(type = FieldType.Boolean)
    private Boolean isActive; // Da li je stanje aktivno u sistemu
}
