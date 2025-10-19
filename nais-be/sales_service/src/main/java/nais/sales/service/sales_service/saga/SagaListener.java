package nais.sales.service.sales_service.saga;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import jakarta.annotation.PostConstruct;
import nais.sales.service.sales_service.dto.PriceListEventDto;
import nais.sales.service.sales_service.model.PriceListEvent;
import nais.sales.service.sales_service.service.PriceListEventService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

@Component
public class SagaListener {
    private final Connection natsConnection;
    private final PriceListEventService priceListEventService;
    private ObjectMapper objectMapper;

    public SagaListener(Connection natsConnection, PriceListEventService priceListEventService, ObjectMapper objectMapper) {
        this.natsConnection = natsConnection;
        this.priceListEventService = priceListEventService;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        System.out.println("[Saga listener]: created connection and recieved!");
    }

    @PostConstruct
    public void startListen() {
        System.out.println("[SAGA LISTENER] Spreman i slušam na 'saga.variant.create'");

        Dispatcher dispatcher = natsConnection.createDispatcher(msg -> {

            try {
                String json = new String(msg.getData(), StandardCharsets.UTF_8);
                System.out.println("[SAGA] Primljen JSON payload: " + json);
                PriceListEventDto dto = objectMapper.readValue(json, PriceListEventDto.class);

                PriceListEvent eventToCreate = PriceListEvent.builder()
                        .price_list_id(dto.getPrice_list_id())
                        .action(dto.getAction())
                        .event(dto.getEvent())
                        .discount(dto.getDiscount())
                        .quantity(dto.getQuantity())
                        .title(dto.getTitle())
                        .phaseId(dto.getPhaseId())
                        .event_time(dto.getEvent_time() != null ? dto.getEvent_time() : Instant.now())
                        .build();

                PriceListEvent createdEvent = priceListEventService.create(eventToCreate);

                System.out.println(">>> [SAGA] Uspješno kreiran PriceListEvent za ID: " + createdEvent.getPrice_list_id());

                Map<String, Object> successResponse = Map.of(
                        "status", "SUCCESS",
                        "data", createdEvent 
                );
                String responseJson;

                try {
                    responseJson = objectMapper.writeValueAsString(successResponse);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                natsConnection.publish(msg.getReplyTo(), responseJson.getBytes(StandardCharsets.UTF_8));

                System.out.println("[SAGA LISTENER] Odgovor poslat!");
            } catch (Exception e) {
                System.err.println("[SAGA] Greška pri obradi poruke: " + e.getMessage());

                try {
                    Map<String, String> errorResponse = Map.of(
                            "status", "FAILURE",
                            "error", e.getMessage()
                    );
                    String responseJson = objectMapper.writeValueAsString(errorResponse);
                    natsConnection.publish(msg.getReplyTo(), responseJson.getBytes(StandardCharsets.UTF_8));
                } catch (Exception ex) {
                    System.err.println("!!! [SAGA - GREŠKA]: Nije moguće poslati odgovor o grešci. " + ex.getMessage());
                }
            }
        });

        dispatcher.subscribe("saga.variant.create");
    }
}