package com.psc.gx2.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PurchaseDetailResponse {
    private String clientName;
    private String clientCpf;
    private List<ProductResponse> products = new ArrayList<>();
    private int totalQuantity;
    private double totalValue;

    public void addProduct(ProductResponse productResponse) {
        products.add(productResponse);
    }
}
