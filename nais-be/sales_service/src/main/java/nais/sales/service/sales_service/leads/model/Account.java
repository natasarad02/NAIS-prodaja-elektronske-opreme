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

@Node("Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private boolean concrete;

    @Relationship(type = "WORKS_FOR", direction = Relationship.Direction.INCOMING)
    private List<Contact>  contacts;

    @Relationship(type = "HAS_ACCOUNT", direction = Relationship.Direction.INCOMING)
    private List<Lead> leads;
}
