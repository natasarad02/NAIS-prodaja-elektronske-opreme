package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.repository.PromotialOfferRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferAttributionService {

    private final PromotialOfferRepository offerRepo;
    private final EntityManager em;

    @Transactional
    public int attributeOrdersForOffer(Long offerId) {
        var offer = offerRepo.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found: " + offerId));
        if (offer.getStartDate() == null || offer.getExpiredDate() == null) return 0;

        String sql = """
                    INSERT INTO offer_order_match(offer_id, order_id, matched_on)
                    SELECT :offerId, o.id, now()
                    FROM orders o
                    WHERE o.order_date BETWEEN :start AND :end
                      AND EXISTS (
                          SELECT 1
                          FROM benefit_regions br
                          WHERE br.benefit_id = :benefitId
                            AND br.region_id = o.region_id
                      )
                    ON CONFLICT DO NOTHING
                """;

        int inserted = em.createNativeQuery(sql)
                .setParameter("offerId", offer.getId())
                .setParameter("start", offer.getStartDate())
                .setParameter("end", offer.getExpiredDate())
                .setParameter("benefitId", offer.getBenefit().getId())
                .executeUpdate();

        return inserted;
    }
}

