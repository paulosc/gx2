package com.psc.gx2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String code;
    private String type;
    private double price;
    private String vintage;
    private int purchaseYear;
    private int quantity;
    private double totalValue;
}
