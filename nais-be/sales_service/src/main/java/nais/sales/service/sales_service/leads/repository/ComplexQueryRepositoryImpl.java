package nais.sales.service.sales_service.leads.repository;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.*;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ComplexQueryRepositoryImpl implements ComplexQueryRepository {
    private final Neo4jClient neo4jClient;

    @Override
    public List<AccountRankingDto> getQualifiedLeadRanking(String statusName) {
        String cypher = """
            MATCH (a:Account)<-[:WORKS_FOR]-(c:Contact)
            WITH a, count(c) AS TotalContacts
            MATCH (a)<-[:HAS_ACCOUNT]-(l:Lead)-[:IS_STATUS]->(s:LeadStatus {name: $statusName})
            WITH a.name AS AccountName, TotalContacts, count(l) AS StatusLeadsCount
            RETURN AccountName, TotalContacts, StatusLeadsCount
            ORDER BY StatusLeadsCount DESC, TotalContacts DESC
            """;

        List<Map<String, Object>> results = neo4jClient.query(cypher)
                .bind(statusName).to("statusName")
                .fetch()
                .all()
                .stream().toList();

        return results.stream()
                .map(map -> new AccountRankingDto(
                        (String) map.get("AccountName"),
                        (Long) map.get("TotalContacts"),
                        (Long) map.get("StatusLeadsCount")
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeadStatusSummaryDto> getOldLeadStatusSummary(String lifecycleName) {
        String cypher = """
        WITH localdatetime() - duration({days: 30}) AS CutoffDate
        
        MATCH (l:Lead)-[:HAS_LIFECYCLE]->(lc:LeadLifecycle {name: $lifecycleName})
        MATCH (l)-[:IS_STATUS]->(s:LeadStatus)
        MATCH (lc)-[:OWNS]->(s) 
        
        WHERE l.createdAt < CutoffDate
        
        WITH s.name AS Status, l, duration.inDays(localdatetime(l.createdAt), localdatetime()).days AS LeadAgeDays
        
        RETURN Status,
               count(l) AS TotalLeads,
               round(avg(LeadAgeDays)) AS AverageLeadAgeDays,
               min(l.createdAt) AS OldestLeadCreatedAt
        ORDER BY AverageLeadAgeDays DESC
        """;

        List<Map<String, Object>> results = neo4jClient.query(cypher)
                .bind(lifecycleName).to("lifecycleName")
                .fetch()
                .all()
                .stream().toList();

        return results.stream()
                .map(map -> {
                    Object rawTime = map.get("OldestLeadCreatedAt");
                    LocalDateTime ldt = null;

                    if (rawTime instanceof ZonedDateTime) {
                        ldt = ((ZonedDateTime) rawTime).toLocalDateTime();
                    } else if (rawTime instanceof LocalDateTime) {
                        ldt = (LocalDateTime) rawTime;
                    }

                    return new LeadStatusSummaryDto(
                            (String) map.get("Status"),
                            (Long) map.get("TotalLeads"),
                            ((Number) map.get("AverageLeadAgeDays")).longValue(),
                            ldt
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<LeadDensityRecommendationDto> getHighDensityAccounts(List<String> earlyStageNames) {
        String cypher = """
            MATCH (a:Account)<-[:WORKS_FOR]-(c:Contact)
            MATCH (a)<-[:HAS_ACCOUNT]-(l:Lead)
            WITH a, count(DISTINCT c) AS TotalContacts, count(DISTINCT l) AS TotalLeads, collect(l) AS AccountLeads
            WITH a.name AS AccountName, TotalContacts, TotalLeads, (toFloat(TotalLeads) / TotalContacts) AS LeadDensity, AccountLeads
            WHERE TotalContacts > 0 AND TotalLeads > 0 AND LeadDensity > 1.5
            MATCH (earlyLead)-[:IS_STATUS]->(s:LeadStatus)
            WHERE earlyLead IN AccountLeads AND s.name IN $earlyStageNames
            WITH AccountName, TotalContacts, TotalLeads, LeadDensity, count(earlyLead) AS EarlyStageLeadsCount
            RETURN AccountName,
                   TotalContacts,
                   TotalLeads,
                   round(LeadDensity * 10) / 10 AS AverageLeadsPerContact,
                   EarlyStageLeadsCount,
                   'Potential Hotspot: High Lead density suggests a strong relationship. Focus on converting the ' + EarlyStageLeadsCount + ' early-stage leads.' AS Recommendation
            ORDER BY LeadDensity DESC
            """;

        List<Map<String, Object>> results = neo4jClient.query(cypher)
                .bind(earlyStageNames).to("earlyStageNames")
                .fetch()
                .all()
                .stream().toList();

        return results.stream()
                .map(map -> new LeadDensityRecommendationDto(
                        (String) map.get("AccountName"),
                        (Long) map.get("TotalContacts"),
                        (Long) map.get("TotalLeads"),
                        (Double) map.get("AverageLeadsPerContact"),
                        (Long) map.get("EarlyStageLeadsCount"),
                        (String) map.get("Recommendation")
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<LeadUpdateResultDto> updateContactedLeadsToQualified(String fromStatus, String toStatus) {
        String cypher = """
            MATCH (a:Account {concrete: true})
            MATCH (a)<-[:HAS_ACCOUNT]-(l:Lead)
            MATCH (l)-[r:IS_STATUS]->(currentStatus:LeadStatus {name: $fromStatus})
            MATCH (nextStatus:LeadStatus {name: $toStatus})
            
            DELETE r
            
            CREATE (l)-[:IS_STATUS]->(nextStatus)
            
            RETURN elementId(l) AS id, l.description, $fromStatus AS OldStatus, nextStatus.name AS NewStatus, a.name AS AccountName
            """;

        List<Map<String, Object>> results = neo4jClient.query(cypher)
                .bind(fromStatus).to("fromStatus")
                .bind(toStatus).to("toStatus")
                .fetch()
                .all()
                .stream().toList();

        return results.stream()
                .map(map -> new LeadUpdateResultDto(
                        (String) map.get("id"),
                        (String) map.get("description"),
                        (String) map.get("OldStatus"),
                        (String) map.get("NewStatus"),
                        (String) map.get("AccountName")
                ))
                .collect(Collectors.toList());
    }

    @Override
    public DeletionSummaryDto deleteOldPartnerApprovedLeads(String lifecycleName, String statusName) {
        String cypher = """
            MATCH (l:Lead)-[:HAS_LIFECYCLE]->(lc:LeadLifecycle {name: $lifecycleName})
            MATCH (l)-[:IS_STATUS]->(s:LeadStatus {name: $statusName})
            WHERE l.createdAt < datetime('2025-09-01T00:00:00')
            MATCH (l)-[:HAS_CONTACT]->(c:Contact)
            
            WITH collect(l) AS LeadsToDelete, collect(DISTINCT c) AS ContactsToCheck
            UNWIND LeadsToDelete AS lead
            DETACH DELETE lead
            
            WITH ContactsToCheck
            
            UNWIND ContactsToCheck AS c
            MATCH (c)
            WHERE NOT EXISTS { (c)<-[:HAS_CONTACT]-(:Lead) }
            AND c.concrete = false
            
            DETACH DELETE c
            
            RETURN 'Deleted Contacts:' AS Report, count(c) AS DeletedContactsCount
            """;

        Optional<DeletionSummaryDto> result = neo4jClient.query(cypher)
                .bind(lifecycleName).to("lifecycleName")
                .bind(statusName).to("statusName")
                .fetchAs(DeletionSummaryDto.class)
                .one();

        return result.orElse(new DeletionSummaryDto("Deleted Contacts:", 0L));
    }
}
