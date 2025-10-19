package nais.sales.service.sales_service.service.impl;

import nais.sales.service.sales_service.dto.OfferSimpleImpactDto;
import nais.sales.service.sales_service.repository.OfferOrderMatchRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OfferImpactReadService {

    private final OfferOrderMatchRepository matchRepo;
    private final EntityManager em;

    private static BigDecimal toBigDecimal(Object v) {
        if (v == null) return BigDecimal.ZERO;
        if (v instanceof BigDecimal bd) return bd;
        if (v instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
        return new BigDecimal(v.toString());
    }

    private static long toLong(Object v) {
        if (v == null) return 0L;
        if (v instanceof Number n) return n.longValue();
        return Long.parseLong(v.toString());
    }

    @Transactional(readOnly = true)
    public OfferSimpleImpactDto getSimpleImpact(Long offerId) {
        String sql = """
                    SELECT COALESCE(SUM(oi.quantity * oi.unit_price), 0) AS revenue,
                           COALESCE(SUM(oi.quantity), 0)                 AS units,
                           COUNT(DISTINCT o.id)                          AS orders
                    FROM offer_order_match m
                    JOIN orders       o  ON o.id = m.order_id
                    JOIN order_items  oi ON oi.order_id = o.id
                    WHERE m.offer_id = :offerId
                """;

        var query = em.createNativeQuery(sql).unwrap(org.hibernate.query.NativeQuery.class);
        Object[] row = (Object[]) query
                .setParameter("offerId", offerId)
                .getSingleResult();

        // Robusno kastovanje
        BigDecimal revenue = toBigDecimal(row[0]);
        long units = toLong(row[1]);
        long orders = toLong(row[2]);

        return new OfferSimpleImpactDto(
                offerId,
                orders,
                revenue,
                units
        );
    }
}

