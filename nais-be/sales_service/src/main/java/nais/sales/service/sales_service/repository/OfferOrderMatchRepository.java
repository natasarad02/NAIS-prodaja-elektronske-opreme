package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.OfferOrderMatch;
import nais.sales.service.sales_service.model.OfferOrderMatchId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferOrderMatchRepository extends JpaRepository<OfferOrderMatch, OfferOrderMatchId> {
    long countByIdOfferId(Long offerId);

    List<OfferOrderMatch> findByIdOfferId(Long offerId);
}
