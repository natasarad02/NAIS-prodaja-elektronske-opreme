package nais.sales.service.sales_service.leads.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.UUID;

@Node("Lead")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {
    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private LocalDateTime createdAt;

    @Relationship(type = "HAS_CONTACT", direction = Relationship.Direction.OUTGOING)
    private Contact contact;

    @Relationship(type = "HAS_ACCOUNT", direction = Relationship.Direction.OUTGOING)
    private Account account;

    @Relationship(type = "HAS_LIFECYCLE", direction = Relationship.Direction.OUTGOING)
    private LeadLifecycle leadLifecycle;

    @Relationship(type = "IS_STATUS", direction = Relationship.Direction.OUTGOING)
    private LeadStatus leadStatus;
}
