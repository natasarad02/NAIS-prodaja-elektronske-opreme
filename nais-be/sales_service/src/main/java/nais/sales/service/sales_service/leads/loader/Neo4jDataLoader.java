package nais.sales.service.sales_service.leads.loader;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class Neo4jDataLoader {

    private static final int MAX_RETRIES = 10;
    private static final long RETRY_DELAY_MS = 2000;

    @Bean
    ApplicationRunner loadNeo4jData(Neo4jClient neo4jClient) {
        return args -> {
            waitForNeo4j(neo4jClient);

            boolean hasLeads = neo4jClient.query("MATCH (n:Lead) RETURN COUNT(n) > 0 AS exists")
                    .fetchAs(Boolean.class)
                    .one()
                    .orElse(false);

            if (!hasLeads) {
                System.out.println("🌱 Seeding Neo4j database from data/seed.cypher...");
                seedDatabase(neo4jClient);
                System.out.println("✅ Seeding completed!");
            } else {
                System.out.println("ℹ️ Database already contains data, skipping seeding.");
            }
        };
    }

    private void waitForNeo4j(Neo4jClient neo4jClient) throws InterruptedException {
        int retries = MAX_RETRIES;
        while (retries > 0) {
            try {
                neo4jClient.query("RETURN 1").run();
                System.out.println("✅ Connected to Neo4j!");
                return;
            } catch (Exception e) {
                System.out.println("⏳ Waiting for Neo4j to be ready...");
                Thread.sleep(RETRY_DELAY_MS);
                retries--;
            }
        }
        throw new RuntimeException("❌ Neo4j not ready after multiple retries!");
    }

    @Transactional
    protected void seedDatabase(Neo4jClient neo4jClient) {
        try {
            var resource = new ClassPathResource("data/seed.cypher");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String cypher = reader.lines().collect(Collectors.joining("\n"));

                for (String statement : cypher.split(";")) {
                    String trimmed = statement.trim();
                    if (!trimmed.isEmpty()) {
                        System.out.println("Executing: " + trimmed);
                        neo4jClient.query(trimmed).run();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to seed Neo4j database", e);
        }
    }
}