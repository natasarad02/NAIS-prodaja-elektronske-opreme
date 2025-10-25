package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.dto.VariantDto;
import nais.sales.service.sales_service.leads.model.Lead;
import nais.sales.service.sales_service.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LeadVariantService {

    private final VariantClientService variantClientService;
    private final LeadRepository leadRepository;

    @Transactional
    public Lead addVariantToWishlist(String leadId, VariantDto variantDTO) {
        VariantDto createdVariant = variantClientService.createVariantInPython(variantDTO);

        if (createdVariant == null || createdVariant.getId() == null) {
            throw new RuntimeException("Variant creation failed in Python service (null return or missing ID). Check logs for details.");
        }

        try {
            Lead lead = leadRepository.findById(UUID.fromString(leadId))
                    .orElseThrow(() -> new RuntimeException("Lead not found"));

            if (lead.getWishlist() == null) {
                lead.setWishlist(new ArrayList<>());
            }

            System.out.println("Lead found, adding variant to wishlist.");
            lead.getWishlist().add(createdVariant.getId());

            return leadRepository.save(lead);

        } catch (Exception e) {
            System.out.println("!!!ERROR!!! Local transaction failed, executing SAGA compensation step (deleting remote variant).");

            try {
                variantClientService.deleteVariantInPython(createdVariant.getId(), createdVariant.getProductId());
                System.out.println("Compensation successful: Variant deleted from Python service (ID: " + createdVariant.getId() + ")");
            } catch (Exception deleteException) {
                System.err.println("CRITICAL SAGA FAILURE: Could not delete variant ID " + createdVariant.getId() + " during rollback! Manual cleanup required.");
                deleteException.printStackTrace();
            }

            throw new RuntimeException("Error adding variant to wishlist, compensation executed.", e);
        }
    }
}
