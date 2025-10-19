package nais.sales.service.sales_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.nats.client.Connection;
import io.nats.client.Options;

@Configuration
public class NatsConfig {

    @Bean(destroyMethod = "close")
    public Connection natsConnection() throws Exception {

        String url = firstNonBlank(
                System.getProperty("nats.url"),
                System.getenv("NATS_URL"),
                "nats://localhost:4222"
        );

        System.out.println("### [NATS CONFIG] Pokušavam da se povežem na NATS adresu: " + url + " ###");

        Options opts = new Options.Builder()
                .server(url)
                .connectionName("sales-service")
                .connectionTimeout(java.time.Duration.ofSeconds(10))
                .maxReconnects(-1)
                .reconnectWait(java.time.Duration.ofSeconds(2))

                .connectionListener((conn, type) -> {
                    System.out.println("### [NATS STATUS] Novi status konekcije: " + type + " ###");
                })
                .build();

        System.out.println("[NATS] Connecting to: " + url);

        return io.nats.client.Nats.connect(opts);
    }

    private static String firstNonBlank(String... vals) {
        for (String v : vals) if (v != null && !v.isBlank()) return v;
        return null;
    }
}