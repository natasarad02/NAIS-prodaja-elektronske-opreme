package nais.sales.service.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nais.sales.service.sales_service.enums.OfferStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotialOfferDto {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate expiredDate;
    private Long benefitId;
    private OfferStatus status;
}
