package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.Contact;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends Neo4jRepository<Contact, UUID> {
}
