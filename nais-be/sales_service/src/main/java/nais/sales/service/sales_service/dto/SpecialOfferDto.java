package nais.sales.service.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialOfferDto {
    private Long id;
    private String title;
    private Long userId;
    private LocalDate paymentDeadline;
    private Integer paymentPeriod;
    private Long benefitId;
}