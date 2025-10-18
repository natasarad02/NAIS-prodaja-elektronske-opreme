package nais.sales.service.sales_service.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import nais.sales.service.sales_service.model.PriceListEvent;

@Repository
@RequiredArgsConstructor
public class PriceListEventRepository {

    private final InfluxDBClient client;

    @Value("${influx.bucket}") private String bucket;
    @Value("${influx.org}")    private String org;

    private WriteApiBlocking writeApi() { return client.getWriteApiBlocking(); }
    private QueryApi queryApi()         { return client.getQueryApi(); }

    public void save(PriceListEvent e) {
        if (e.getEvent_time() == null) e.setEvent_time(Instant.now());
        if (e.getEvent() == null)      e.setEvent(1L);
        writeApi().writeMeasurement(WritePrecision.NS, e);
    }

    public List<PriceListEvent> timeline(String priceListId, Instant from, Instant to) {
        String flux =
                "from(bucket: \"" + bucket + "\")"
                        + " |> range(start: time(v: \"" + from.toString() + "\"), stop: time(v: \"" + to.toString() + "\"))"
                        + " |> filter(fn: (r) => r[\"_measurement\"] == \"price_list_event\" and r[\"price_list_id\"] == \"" + priceListId + "\")"
                        + " |> pivot(rowKey:[\"_time\"], columnKey:[\"_field\"], valueColumn:\"_value\")"
                        + " |> keep(columns:[\"_time\",\"price_list_id\",\"action\",\"event\",\"discount\",\"quantity\",\"title\",\"phase_id\"])"
                        + " |> sort(columns:[\"_time\"], desc:true)";

        return queryApi().query(flux, PriceListEvent.class);
    }

    public void deleteByPriceListId(String id, Instant from, Instant to) {
        String predicate = "_measurement=\"price_list_event\" AND price_list_id=\"" + id + "\"";
        client.getDeleteApi().delete(
                from.atOffset(ZoneOffset.UTC),
                to.atOffset(ZoneOffset.UTC),
                predicate,
                bucket,
                org
        );
    }
}

