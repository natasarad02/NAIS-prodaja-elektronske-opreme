package nais.sales.service.sales_service.leads.service;

import nais.sales.service.sales_service.leads.dto.LeadDto;
import nais.sales.service.sales_service.leads.dto.VariantDto;
import nais.sales.service.sales_service.leads.model.Lead;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface LeadService {
    Lead create(Lead lead);
    Optional<Lead> findById(UUID id);
    List<Lead> findAll();
    Lead update(Lead lead);
    boolean deleteById(UUID id);
    Lead checkExists(UUID id);
    List<LeadDto> getWithStatus(UUID statusId);
}
