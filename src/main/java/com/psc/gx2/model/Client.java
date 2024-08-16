package com.psc.gx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Client {
    @JsonProperty("nome")
    private String name;
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("compras")
    private List<Purchase> purchases;
}
