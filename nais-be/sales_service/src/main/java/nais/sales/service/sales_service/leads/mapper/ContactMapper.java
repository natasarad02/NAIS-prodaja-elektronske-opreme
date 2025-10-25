package nais.sales.service.sales_service.leads.mapper;

import nais.sales.service.sales_service.leads.dto.ContactDto;
import nais.sales.service.sales_service.leads.model.Account;
import nais.sales.service.sales_service.leads.model.Contact;

public class ContactMapper {
    public static ContactDto toDto(Contact entity) {
        return ContactDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .concrete(entity.isConcrete())
                .accountId(entity.getAccount() != null
                        ? entity.getAccount().getId()
                        : null)
                .build();
    }

    public static Contact toEntity(ContactDto dto) {
        return Contact.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .concrete(dto.isConcrete())
                .account(dto.getAccountId() != null
                        ? Account.builder()
                        .id(dto.getAccountId())
                        .build()
                        : null)
                .build();
    }
}
