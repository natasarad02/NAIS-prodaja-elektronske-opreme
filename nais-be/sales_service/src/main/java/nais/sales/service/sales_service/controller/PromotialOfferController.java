package nais.sales.service.sales_service.controller;

import nais.sales.service.sales_service.dto.AverageOfferDurationDto;
import nais.sales.service.sales_service.dto.BenefitTypeCountViewDto;
import nais.sales.service.sales_service.dto.PromotialOfferDto;
import nais.sales.service.sales_service.dto.PromotialOfferStatusCountDto;
import nais.sales.service.sales_service.service.PromotialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotial-offer")
@RequiredArgsConstructor
public class PromotialOfferController {

    private final PromotialOfferService promotialOfferService;

    @PostMapping
    public PromotialOfferDto create(@RequestBody PromotialOfferDto promotialOfferDto) {
        return promotialOfferService.createPromotialOffer(promotialOfferDto);
    }

    @GetMapping
    public List<PromotialOfferDto> getPromotialOffers() {
        return promotialOfferService.getPromotialOffers();
    }

    @GetMapping("/{id}")
    public PromotialOfferDto getPromotialOffer(@PathVariable Long id) {
        return promotialOfferService.getPromotialOfferById(id);
    }

    @PutMapping("/{id}")
    public PromotialOfferDto update(@PathVariable Long id, @RequestBody PromotialOfferDto promotialOfferDto) {
        return promotialOfferService.updatePromotialOffer(id, promotialOfferDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePromotialOffer(@PathVariable Long id) {
        promotialOfferService.deletePromotialOffer(id);
    }

    @GetMapping("/analytics/average-duration")
    public AverageOfferDurationDto getAverageDuration() {
        Double days = promotialOfferService.getAverageOfferDurationDays();
        return new AverageOfferDurationDto(days);
    }

    @GetMapping("/analytics/benefit-types")
    public List<BenefitTypeCountViewDto> getBenefitTypeCounts() {
        return promotialOfferService.getBenefitTypeCounts();
    }

    @GetMapping("/analytics/status-counts")
    public PromotialOfferStatusCountDto getStatusCounts() {
        return promotialOfferService.getOfferStatusCounts();
    }
}

