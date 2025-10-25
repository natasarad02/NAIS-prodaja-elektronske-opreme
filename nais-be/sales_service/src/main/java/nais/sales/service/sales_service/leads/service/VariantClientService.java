package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.dto.VariantDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class VariantClientService {

    private final RestTemplate restTemplate =  new RestTemplate();

    public VariantDto createVariantInPython(VariantDto variantDTO) {
        String url = "http://products_maintenance:8080/variants/";

        try {
            return restTemplate.postForObject(url, variantDTO, VariantDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteVariantInPython(Long variantId, Long productId) {
        String baseUrl = "http://products_maintenance:8080/variants/{variantId}";
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("product_id", productId)
                .buildAndExpand(variantId)
                .toUriString();

        try {
            restTemplate.delete(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
