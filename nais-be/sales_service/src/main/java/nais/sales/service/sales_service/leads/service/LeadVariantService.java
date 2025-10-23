package nais.sales.service.sales_service.leads.service;

import lombok.RequiredArgsConstructor;
import nais.sales.service.sales_service.leads.dto.VariantDto;
import nais.sales.service.sales_service.leads.model.Lead;
import nais.sales.service.sales_service.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeadVariantService {

    private final VariantClientService variantClientService;
    private final LeadRepository leadRepository;

    @Transactional
    public Lead addVariantToWishlist(String leadId, VariantDto variantDTO) {
        VariantDto createdVariant = variantClientService.createVariantInPython(variantDTO);

        if (createdVariant == null || createdVariant.getId() == null) {
            throw new RuntimeException("Variant creation failed in Python service");
        }

        try {
            Lead lead = leadRepository.findById(UUID.fromString(leadId))
                    .orElseThrow(() -> new RuntimeException("Lead not found"));

            if (lead.getWishlist() == null) {
                lead.setWishlist(new ArrayList<>());
            }

            lead.getWishlist().add(createdVariant.getId());

            return leadRepository.save(lead);

        } catch (RuntimeException e) {
            variantClientService.deleteVariantInPython(createdVariant.getId());
            throw new RuntimeException("Error adding variant to wishlist", e);
        }
    }
}