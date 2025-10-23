package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.Lead;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface LeadRepository extends Neo4jRepository<Lead, UUID> {
    @Query("MATCH (l:Lead)-[:IS_STATUS]->(s:LeadStatus) " +
            "WHERE s.id = $statusId " +
            "RETURN l")
    List<Lead> findLeadsByStatusId(@Param("statusId") UUID statusId);
}
