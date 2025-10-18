package nais.sales.service.sales_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NatsConfig {

    @Bean(destroyMethod = "close")
    public io.nats.client.Connection natsConnection() throws Exception {

        String url = firstNonBlank(
                System.getProperty("nats.url"),
                System.getenv("NATS_URL"),
                "nats://localhost:4222"
        );

        io.nats.client.Options opts = new io.nats.client.Options.Builder()
                .server(url)
                .connectionName("sales-service")
                .connectionTimeout(java.time.Duration.ofSeconds(2))
                .maxReconnects(-1)
                .reconnectWait(java.time.Duration.ofSeconds(1))
                .build();

        System.out.println("[NATS] Connecting to: " + url);
        return io.nats.client.Nats.connect(opts);
    }

    private static String firstNonBlank(String... vals) {
        for (String v : vals) if (v != null && !v.isBlank()) return v;
        return null;
    }
}
