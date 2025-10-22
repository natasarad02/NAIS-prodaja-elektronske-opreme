package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.Account;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends Neo4jRepository<Account, UUID> {
}
