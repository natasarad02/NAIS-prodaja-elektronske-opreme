package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.LeadLifecycle;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LeadLifecycleRepository extends Neo4jRepository<LeadLifecycle, UUID> {
}
