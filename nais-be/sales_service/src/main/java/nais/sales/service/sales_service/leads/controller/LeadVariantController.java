package nais.sales.service.sales_service.leads.controller;

import lombok.AllArgsConstructor;
import nais.sales.service.sales_service.leads.dto.LeadDto;
import nais.sales.service.sales_service.leads.dto.VariantDto;
import nais.sales.service.sales_service.leads.mapper.LeadMapper;
import nais.sales.service.sales_service.leads.service.LeadVariantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saga")
@AllArgsConstructor
public class LeadVariantController {
    private final LeadVariantService leadVariantService;

    @PostMapping("/{leadId}")
    public ResponseEntity<LeadDto> addVariantToWishlist(@RequestBody VariantDto variantDto, @PathVariable("leadId") String leadId){
        System.out.println("addVariantToWishlist called for Lead ID: " + leadId);
        LeadDto ret = LeadMapper.toDto(leadVariantService.addVariantToWishlist(leadId, variantDto));
        return ResponseEntity.ok(ret);
    }
}
