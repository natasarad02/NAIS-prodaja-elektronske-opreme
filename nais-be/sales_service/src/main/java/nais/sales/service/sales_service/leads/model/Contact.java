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

@Node("Contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean concrete;

    @Relationship(type = "WORKS_FOR", direction = Relationship.Direction.OUTGOING)
    private Account account;

    @Relationship(type = "HAS_CONTACT", direction = Relationship.Direction.INCOMING)
    private List<Lead> leads;
}
