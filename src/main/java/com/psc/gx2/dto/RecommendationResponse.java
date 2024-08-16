package com.psc.gx2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendationResponse {
    private String name;
    private String cpf;
    private String type;
}
