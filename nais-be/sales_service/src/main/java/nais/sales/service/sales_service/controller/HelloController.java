package nais.sales.service.sales_service.controller;

import org.springframework.web.bind.annotation.*;

import nais.sales.service.sales_service.model.HelloNats;

import org.springframework.http.ResponseEntity;

@RestController
public class HelloController {
  private final HelloNats helloNats;
  public HelloController(HelloNats helloNats) { this.helloNats = helloNats; }

  @PostMapping("/test/hello")
  public ResponseEntity<String> hello() throws Exception {
    helloNats.sendHello();
    return ResponseEntity.ok("Poslato: Zdravo product");
  }
}
