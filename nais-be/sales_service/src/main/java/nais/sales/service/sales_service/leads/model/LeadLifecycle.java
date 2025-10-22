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
import java.util.List;

@Node("LeadLifecycle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadLifecycle {
    @Id @GeneratedValue
    private UUID id;
    private String name;
    private String description;

    @Relationship(type = "OWNS", direction = Relationship.Direction.OUTGOING)
    private List<LeadStatus> leadStatuses;

    @Relationship(type = "HAS_LIFECYCLE", direction = Relationship.Direction.INCOMING)
    private List<Lead> leads;
}
