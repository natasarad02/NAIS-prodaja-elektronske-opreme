package nais.sales.service.sales_service.repository;

import nais.sales.service.sales_service.model.AdministratorNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorNotifyRepository extends JpaRepository<AdministratorNotify, Long> {

    List<AdministratorNotify> findAllByPriceList_Id(Long priceListId);
}
