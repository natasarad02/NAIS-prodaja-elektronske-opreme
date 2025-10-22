package nais.sales.service.sales_service.leads.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.UUID;

@Node("LeadStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadStatus {
    @Id @GeneratedValue
    private UUID id;
    private String name;
    private String description;

    @Relationship(type = "OWNS", direction = Relationship.Direction.INCOMING)
    private LeadLifecycle lifecycle;

    @Relationship(type = "IS_NEXT", direction = Relationship.Direction.INCOMING)
    private LeadStatus previous;

    @Relationship(type = "IS_NEXT", direction = Relationship.Direction.OUTGOING)
    private LeadStatus next;
}
