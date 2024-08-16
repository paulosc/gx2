package com.psc.gx2.controller;

import com.psc.gx2.dto.PurchaseDetailResponse;
import com.psc.gx2.dto.RecommendationResponse;
import com.psc.gx2.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/compras")
    public List<PurchaseDetailResponse> getPurchases() {
        return purchaseService.getAllPurchasesSortedByValue();
    }

    @GetMapping("/maior-compra/{year}")
    public PurchaseDetailResponse getBiggestPurchaseOfTheYear(@PathVariable Integer year) {
        return purchaseService.getBiggestPurchaseOfTheYear(year);
    }

    @GetMapping("/clientes-fieis")
    public List<PurchaseDetailResponse> getLoyalCustomers() {
        return purchaseService.getLoyalCustomers();
    }

    @GetMapping("/recomendacao/cliente/tipo")
    public List<RecommendationResponse> getCustomerRecommendationByType() {
        return purchaseService.getCustomerRecommendationByType();
    }

}
