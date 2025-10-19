package nais.sales.service.sales_service.seeder;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Order(2)
public class PriceListEventSeeder implements CommandLineRunner {
    private final InfluxDBClient client;

    @Value("${influx.bucket:nais_bucket}") String bucket;

    @Override public void run(String... args) {
        WriteApiBlocking write = client.getWriteApiBlocking();
        Random rng = new Random(77);
        Instant now = Instant.now();

        List<String> lines = new ArrayList<>(2200);
        for (int i = 0; i < 2200; i++) {
            String priceListId = "PL-" + (1 + rng.nextInt(60));
            String action = switch (rng.nextInt(3)) { case 0 -> "CREATE"; case 1 -> "UPDATE"; default -> "DELETE"; };
            int q = 1 + rng.nextInt(10);
            double disc = rng.nextDouble() * 30.0;
            int phase = 1 + rng.nextInt(4);
            long tsNs = now.minusSeconds(rng.nextInt(30 * 24 * 3600)).toEpochMilli() * 1_000_000L;

            String lp = String.format(
                    "price_list_event,price_list_id=%s,action=%s event=1i,discount=%f,quantity=%di,phase_id=%di,title=\"PL %s\" %d",
                    priceListId, action, disc, q, phase, priceListId, tsNs
            );
            lines.add(lp);
        }
        write.writeRecords(WritePrecision.NS, lines);
        System.out.println("Seeded ~2200 price_list_event points.");
    }
}
