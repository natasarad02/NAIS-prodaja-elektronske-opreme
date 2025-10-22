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
    private String stateName; // Naziv stanja (npr. "OTVORENO", "U_OBRADI", "ČEKA_DELOVE")
    
    @Field(type = FieldType.Text)
    private String stateDescription; // Opis stanja
    
    @Field(type = FieldType.Integer)
    private Integer orderSequence; // Redosled u životnom ciklusu (1, 2, 3...)
    
    @Field(type = FieldType.Boolean)
    private Boolean isInitialState; // Da li je početno stanje
    
    @Field(type = FieldType.Boolean)
    private Boolean isFinalState; // Da li je završno stanje
    
    @Field(type = FieldType.Keyword)
    private String stateCategory; // Kategorija: "ACTIVE", "WAITING", "COMPLETED", "CANCELLED"
    
    @Field(type = FieldType.Boolean)
    private Boolean requiresCustomerAction; // Da li zahteva akciju korisnika
    
    @Field(type = FieldType.Boolean)
    private Boolean isActive; // Da li je stanje aktivno u sistemu
}
