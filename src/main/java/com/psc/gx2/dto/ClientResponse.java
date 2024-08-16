package com.psc.gx2.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
    private String name;
    private String cpf;
    private List<PurchaseResponse> purchases;
}
