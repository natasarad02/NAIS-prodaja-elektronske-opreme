package nais.sales.service.sales_service.leads.service;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.VariantDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class VariantClientService {
    private final WebClient webClient;

    public VariantDto createVariantInPython(VariantDto variantDTO) {
        return webClient.post()
                .uri("http://localhost:8000/variants/")
                .bodyValue(variantDTO)
                .retrieve()
                .bodyToMono(VariantDto.class)
                .block();
    }

    public void deleteVariantInPython(Long variantId) {
        webClient.delete()
                .uri("http://localhost:8000/variants/{id}", variantId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
