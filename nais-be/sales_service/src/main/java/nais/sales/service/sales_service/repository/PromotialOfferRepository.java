package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.PromotialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotialOfferRepository extends JpaRepository<PromotialOffer, Long> {

    @Query(value = """
                SELECT AVG( (expired_date - start_date) + 1 )::float
                FROM promotial_offers
                WHERE start_date IS NOT NULL AND expired_date IS NOT NULL
            """, nativeQuery = true)
    Double avgDurationDays();

    @Query(value = """
                SELECT b.benefit_type AS type, COUNT(*) AS cnt
                FROM special_benefits b
                JOIN promotial_offers o ON o.benefit_id = b.id
                GROUP BY b.benefit_type
            """, nativeQuery = true)
    List<Object[]> countByBenefitTypeRaw();
}
