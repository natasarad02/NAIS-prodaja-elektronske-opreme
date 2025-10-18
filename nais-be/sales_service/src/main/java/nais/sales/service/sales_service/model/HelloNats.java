package nais.sales.service.sales_service.model;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class HelloNats {
  private final Connection nc;

  public HelloNats(Connection nc) { this.nc = nc; }

  @PostConstruct
  public void init() {

    Dispatcher d = nc.createDispatcher(msg -> {
      String text = new String(msg.getData(), StandardCharsets.UTF_8);
      System.out.println("[SALES] Odgovor: " + text);
    });
    d.subscribe("hello.sales");
  }

  public void sendHello() throws Exception {
    String payload = "Zdravo product";
    nc.publish("hello.product", payload.getBytes(StandardCharsets.UTF_8));
  }
}