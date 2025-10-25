package nais.sales.service.sales_service.leads.mapper;

import nais.sales.service.sales_service.leads.dto.LeadDto;
import nais.sales.service.sales_service.leads.model.*;

import java.util.ArrayList;

public class LeadMapper {
    public static LeadDto toDto(Lead lead){
        return LeadDto.builder()
                .id(lead.getId())
                .description(lead.getDescription())
                .createdAt(lead.getCreatedAt())
                .lifecycle(lead.getLeadLifecycle() != null
                        ? lead.getLeadLifecycle().getName()
                        : null)
                .status(lead.getLeadStatus() != null
                        ? lead.getLeadStatus().getName()
                        : null)
                .accountId(lead.getAccount() != null ? lead.getAccount().getId() : null)
                .contactId(lead.getContact() != null ? lead.getContact().getId() : null)
                .wishlist(lead.getWishlist() != null
                        ? lead.getWishlist()
                        : new ArrayList<>())
                .build();
    }

    public static Lead toEntity(LeadDto dto){
        return Lead.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .leadLifecycle(dto.getLifecycle() != null
                        ? LeadLifecycle.builder()
                        .name(dto.getLifecycle())
                        .build()
                        : null)
                .leadStatus(dto.getStatus() != null
                        ? LeadStatus.builder()
                        .name(dto.getStatus())
                        .build()
                        : null)
                .account(dto.getAccountId() != null
                        ? Account.builder()
                        .id(dto.getAccountId())
                        .build()
                        : null)
                .contact(dto.getContactId() != null
                        ? Contact.builder()
                        .id(dto.getContactId())
                        .build()
                        : null)
                .build();
    }
}
