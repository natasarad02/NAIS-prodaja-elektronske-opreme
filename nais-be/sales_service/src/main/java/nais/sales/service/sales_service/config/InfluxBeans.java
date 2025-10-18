package nais.sales.service.sales_service.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxBeans {
    @Bean(destroyMethod = "close")
    public InfluxDBClient influxDBClient(
            @Value("${influx.url}") String url,
            @Value("${influx.token}") String token,
            @Value("${influx.org}") String org,
            @Value("${influx.bucket}") String bucket
    ) {
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    }

    @Bean
    public WriteApiBlocking writeApi(InfluxDBClient client) { return client.getWriteApiBlocking(); }

    @Bean
    public QueryApi queryApi(InfluxDBClient client) { return client.getQueryApi(); }
}
