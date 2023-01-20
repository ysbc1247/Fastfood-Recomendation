package com.projects.Fastfood.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DocumentDto {
    @JsonProperty("address_name")
    private String storeAddress;

    @JsonProperty("y")
    private Double latitude;

    @JsonProperty("x")
    private Double longitude;
}
