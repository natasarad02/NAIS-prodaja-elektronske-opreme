package nais.sales.service.sales_service.leads.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadDto {
    private UUID id;
    private String description;
    private LocalDateTime createdAt;
    private String lifecycle;
    private String status;
    private UUID accountId;
    private UUID contactId;
    private List<Long> wishlist = new ArrayList<>();
}
