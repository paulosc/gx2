package com.psc.gx2.service;

import com.psc.gx2.api.ClientAPI;
import com.psc.gx2.api.ProductAPI;
import com.psc.gx2.dto.ProductResponse;
import com.psc.gx2.dto.PurchaseDetailResponse;
import com.psc.gx2.dto.RecommendationResponse;
import com.psc.gx2.model.Client;
import com.psc.gx2.model.Product;
import com.psc.gx2.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final ClientAPI clientAPI;
    private final ProductAPI productAPI;

    public List<PurchaseDetailResponse> getAllPurchasesSortedByValue() {
        List<Client> clients = clientAPI.getClients();
        Map<String, Product> productMap = getProductMap();

        return clients.stream()
                .map(client -> buildPurchaseDetail(client, productMap))
                .sorted(Comparator.comparingDouble(PurchaseDetailResponse::getTotalValue))
                .collect(Collectors.toList());
    }

    public PurchaseDetailResponse getBiggestPurchaseOfTheYear(Integer year) {
        List<Client> clients = clientAPI.getClients();
        Map<String, Product> productMap = getProductMap();

        return clients.stream()
                .flatMap(client -> client.getPurchases().stream()
                        .map(purchase -> buildPurchaseDetailForYear(year, client, purchase, productMap)))
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(PurchaseDetailResponse::getTotalValue))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No purchases found for the specified year."));
    }

    public List<PurchaseDetailResponse> getLoyalCustomers() {
        List<Client> clients = clientAPI.getClients();
        Map<String, Product> productMap = getProductMap();

        return clients.stream()
                .map(client -> buildPurchaseDetail(client, productMap))
                .sorted(Comparator.comparingDouble(PurchaseDetailResponse::getTotalValue)
                        .thenComparingInt(PurchaseDetailResponse::getTotalQuantity)
                        .reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<RecommendationResponse> getCustomerRecommendationByType() {
        List<Client> clients = clientAPI.getClients();
        Map<String, Product> productMap = getProductMap();

        return clients.stream()
                .map(client -> {
                    String mostBoughtWineType = getMostBoughtWineType(client, productMap);
                    return buildRecommendationResponse(client, mostBoughtWineType);
                })
                .collect(Collectors.toList());
    }

    private Map<String, Product> getProductMap() {
        return productAPI.getProducts().stream()
                .collect(Collectors.toMap(Product::getCode, product -> product));
    }

    private PurchaseDetailResponse buildPurchaseDetail(Client client, Map<String, Product> productMap) {
        PurchaseDetailResponse detail = new PurchaseDetailResponse();
        detail.setClientName(client.getName());
        detail.setClientCpf(client.getCpf());

        for (Purchase purchase : client.getPurchases()) {
            Product product = productMap.get(purchase.getCode());
            if (product != null) {
                double totalValue = purchase.getQuantity() * product.getPrice();
                ProductResponse productResponse = buildProductResponse(purchase, product, totalValue);

                detail.addProduct(productResponse);
                detail.setTotalQuantity(detail.getTotalQuantity() + purchase.getQuantity());
                detail.setTotalValue(detail.getTotalValue() + totalValue);
            }
        }

        return detail;
    }

    private PurchaseDetailResponse buildPurchaseDetailForYear(int year, Client client, Purchase purchase, Map<String, Product> productMap) {
        Product product = productMap.get(purchase.getCode());

        if (product != null && product.getPurchaseYear() == year) {
            PurchaseDetailResponse detail = new PurchaseDetailResponse();
            detail.setClientName(client.getName());
            detail.setClientCpf(client.getCpf());
            detail.setTotalQuantity(purchase.getQuantity());
            double totalValue = purchase.getQuantity() * product.getPrice();
            detail.setTotalValue(totalValue);

            ProductResponse productResponse = buildProductResponse(purchase, product, totalValue);
            detail.addProduct(productResponse);

            return detail;
        }

        return null;
    }

    private ProductResponse buildProductResponse(Purchase purchase, Product product, double totalValue) {
        return ProductResponse.builder()
                .quantity(purchase.getQuantity())
                .purchaseYear(product.getPurchaseYear())
                .code(product.getCode())
                .vintage(product.getVintage())
                .type(product.getWineType())
                .price(product.getPrice())
                .totalValue(totalValue)
                .build();
    }

    private String getMostBoughtWineType(Client client, Map<String, Product> productMap) {
        return client.getPurchases().stream()
                .map(purchase -> productMap.get(purchase.getCode()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Product::getWineType, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private RecommendationResponse buildRecommendationResponse(Client client, String mostBoughtWineType) {
        return RecommendationResponse.builder()
                .name(client.getName())
                .cpf(client.getCpf())
                .type(mostBoughtWineType)
                .build();
    }
}
