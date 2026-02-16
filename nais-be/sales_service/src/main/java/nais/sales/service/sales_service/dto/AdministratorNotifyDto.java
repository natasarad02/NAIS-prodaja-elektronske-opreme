package nais.sales.service.sales_service.dto;

import nais.sales.service.sales_service.enums.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministratorNotifyDto {
    private Long id;
    private String message;
    private NotificationStatus status;
    private Long priceListId;
    private String priceListName;
    private LocalDateTime createdAt;
}