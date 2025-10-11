package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.enums.NotificationStatus;
import nais.sales.service.sales_service.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query("""
                select pl
                from PriceList pl
                where not exists (
                    select 1
                    from AdministratorNotify n
                    where n.priceList = pl
                      and n.status = :status
                )
            """)
    List<PriceList> findAllWithoutStatus(@Param("status") NotificationStatus status);
}
