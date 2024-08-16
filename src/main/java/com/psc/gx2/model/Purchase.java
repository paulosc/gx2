package com.psc.gx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Purchase {
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("quantidade")
    private int quantity;
}
