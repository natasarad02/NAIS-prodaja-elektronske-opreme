package nais.sales.service.sales_service.leads.mapper;

import nais.sales.service.sales_service.leads.dto.AccountDto;
import nais.sales.service.sales_service.leads.model.Account;

import java.util.ArrayList;

public class AccountMapper {
    public static AccountDto toDto(Account entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .concrete(entity.isConcrete())
                .build();
    }

    public static Account toEntity(AccountDto dto) {
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .concrete(dto.isConcrete())
                .contacts(null)
                .leads(null)
                .build();
    }
}
