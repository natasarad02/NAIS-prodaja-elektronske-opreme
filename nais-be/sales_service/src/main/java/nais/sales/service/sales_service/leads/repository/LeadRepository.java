package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.Lead;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LeadRepository extends Neo4jRepository<Lead, UUID> {
}
