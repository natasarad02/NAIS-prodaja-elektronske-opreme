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
@Document(indexName = "service_tickets")
public class ServiceTicket {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Text)
    private String title;
    
    @Field(type = FieldType.Text)
    private String description;
    
    @Field(type = FieldType.Keyword)
    private String serviceType; 
    
    @Field(type = FieldType.Keyword)
    private String currentStateId; 
    
    @Field(type = FieldType.Keyword)
    private String currentStateName;
    
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;
    
    @Field(type = FieldType.Keyword)
    private String customerId;
    
    @Field(type = FieldType.Text)
    private String customerName;
    
    @Field(type = FieldType.Text)
    private String customerEmail;
    
    @Field(type = FieldType.Keyword)
    private String assignedTo;
    
    @Field(type = FieldType.Integer)
    private Integer priority; // 1-5 
    
    @Field(type = FieldType.Text)
    private String customerFeedback;
    
    @Field(type = FieldType.Integer)
    private Integer customerRating;
    
    @Field(type = FieldType.Long)
    private Long totalDuration; // Ukupno trajanje u minutima
    
    @Field(type = FieldType.Text)
    private String internalNotes;
    
    @Field(type = FieldType.Boolean)
    private Boolean isResolved;
    
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime resolvedAt;
    
    @Field(type = FieldType.Keyword)
    private String deviceType;
    
    @Field(type = FieldType.Keyword)
    private String deviceModel;
    
    @Field(type = FieldType.Keyword)
    private String serialNumber;
}
