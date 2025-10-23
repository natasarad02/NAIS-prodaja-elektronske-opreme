package nais.sales.service.sales_service.leads.repository;

import nais.sales.service.sales_service.leads.model.Account;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface AccountRepository extends Neo4jRepository<Account, UUID> {
    @Query("MATCH (a:Account)<-[:HAS_ACCOUNT]-(l:Lead) " +
            "WITH a, COUNT(l) AS leadCount " +
            "WHERE leadCount > $minLeads " +
            "AND a.concrete = true " +
            "RETURN a")
    List<Account> findAccountsWithMoreThanNLeads(@Param("minLeads") long minLeads);
}
