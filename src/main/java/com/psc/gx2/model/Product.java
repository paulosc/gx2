package com.psc.gx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Product {

    @JsonProperty("codigo")
    private String code;

    @JsonProperty("tipo_vinho")
    private String wineType;

    @JsonProperty("preco")
    private double price;

    @JsonProperty("safra")
    private String vintage;

    @JsonProperty("ano_compra")
    private int purchaseYear;
}
