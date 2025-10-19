package nais.sales.service.sales_service.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nais.sales.service.sales_service.model.PriceListEvent;

@Repository
@RequiredArgsConstructor
public class PriceListEventRepository {

    private final InfluxDBClient client;

    @Value("${influx.bucket}") private String bucket;
    @Value("${influx.org}")    private String org;

    private WriteApiBlocking writeApi() { return client.getWriteApiBlocking(); }
    private QueryApi queryApi()         { return client.getQueryApi(); }

    public PriceListEvent save(PriceListEvent e) {
        if (e.getEvent_time() == null) e.setEvent_time(Instant.now());
        if (e.getEvent() == null)      e.setEvent(1L);

        writeApi().writeMeasurement(WritePrecision.NS, e);

        return e;
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

    public List<Map<String, Object>> getActionCounts() {
        String query = """
            from(bucket: "nais_bucket")
              |> range(start: -30d)
              |> filter(fn: (r) => r._measurement == "price_list_event")
              |> group(columns: ["action"])
              |> count()
              |> group()
              |> sort(columns: ["_value"], desc: true)
            """;
        return executeQuery(query);
    }

    public List<Map<String, Object>> getTop5ByAverageDiscount() {
        String query = """
            from(bucket: "nais_bucket")
              |> range(start: -30d)
              |> filter(fn: (r) => r._measurement == "price_list_event")
              |> filter(fn: (r) => r.action == "UPDATE")
              |> filter(fn: (r) => r._field == "discount")
              |> group(columns: ["price_list_id"])
              |> mean()
              |> group()
              |> sort(columns: ["_value"], desc: true)
              |> limit(n: 5)
            """;
        return executeQuery(query);
    }

    public List<Map<String, Object>> getDailyTrendForTop3() {
        String query = 
            "allData = from(bucket: \"nais_bucket\")" +
            "  |> range(start: -14d)" +
            "  |> filter(fn: (r) => r._measurement == \"price_list_event\")" +
            "  |> filter(fn: (r) => r.action == \"UPDATE\" or r.action == \"CREATE\")" +
            "  |> filter(fn: (r) => r._field == \"quantity\")\n" +
            
            "top3_list = from(bucket: \"nais_bucket\")" +
            "  |> range(start: -14d)" +
            "  |> filter(fn: (r) => r._measurement == \"price_list_event\")" +
            "  |> filter(fn: (r) => r._field == \"quantity\")" +
            "  |> group(columns: [\"price_list_id\"])" +
            "  |> sum()" +
            "  |> group()" +
            "  |> sort(columns: [\"_value\"], desc: true)" +
            "  |> limit(n: 3)" +
            "  |> keep(columns: [\"price_list_id\"])\n" +
            
            "join(tables: {data: allData, top: top3_list}, on: [\"price_list_id\"])" +
            "  |> group(columns: [\"price_list_id\"])" +
            "  |> aggregateWindow(every: 1d, fn: sum, createEmpty: true)" +
            "  |> yield(name: \"dnevni_trend_top3_cenovnika\")";
            
        return executeQuery(query);
    }
    
    public List<Map<String, Object>> getDiscountVsQuantityForOne(String priceListId) {
        String query = String.format("""
            from(bucket: "nais_bucket")
              |> range(start: -14d)
              |> filter(fn: (r) => r._measurement == "price_list_event")
              |> filter(fn: (r) => r.price_list_id == "%s")
              |> filter(fn: (r) => r._field == "discount" or r._field == "quantity")
              |> group(columns: ["_field"])
            """, priceListId);
        return executeQuery(query);
    }

    private List<Map<String, Object>> executeQuery(String fluxQuery) {
        List<FluxTable> tables = client.getQueryApi().query(fluxQuery, org);
        List<Map<String, Object>> result = new ArrayList<>();
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                result.add(record.getValues());
            }
        }
        return result;
    }
}

