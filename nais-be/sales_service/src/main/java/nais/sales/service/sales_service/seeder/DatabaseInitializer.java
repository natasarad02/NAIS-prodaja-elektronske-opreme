package nais.sales.service.sales_service.seeder;

import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.DeletePredicateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@Order(1)
public class DatabaseInitializer implements CommandLineRunner {

    private final InfluxDBClient client;

    @Value("${influx.bucket:nais_bucket}")
    private String bucket;

    @Value("${influx.org:nais}")
    private String org;

    public DatabaseInitializer(InfluxDBClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Deleting old data from bucket: " + bucket + " ---");

        DeletePredicateRequest predicate = new DeletePredicateRequest();
        predicate.setStart(OffsetDateTime.parse("1970-01-01T00:00:00Z"));
        predicate.setStop(OffsetDateTime.now());
        predicate.setPredicate("_measurement=\"price_list_event\"");

        DeleteApi deleteApi = client.getDeleteApi();
        deleteApi.delete(predicate, bucket, org);

        System.out.println("--- Old data deleted successfully ---");
    }
}