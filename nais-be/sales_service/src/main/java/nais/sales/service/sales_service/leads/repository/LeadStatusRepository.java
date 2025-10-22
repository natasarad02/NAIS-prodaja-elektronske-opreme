package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.LeadStatus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeadStatusRepository extends Neo4jRepository<LeadStatus, UUID> {
    @Query("""
        MATCH (lc:LeadLifecycle {id: $lifecycleId})-[:OWNS]->(ls:LeadStatus)
        WHERE NOT (ls)<-[:IS_NEXT]-(:LeadStatus)
        RETURN ls
    """)
    Optional<LeadStatus> findFirstStatusInLifecycle(@Param("lifecycleId") UUID lifecycleId);

    @Query("""
        MATCH (current:LeadStatus {id: $statusId})-[r:IS_NEXT]->(:LeadStatus)
        DELETE r
    """)
    void deletePreviousIsNext(@Param("statusId") UUID statusId);
}
