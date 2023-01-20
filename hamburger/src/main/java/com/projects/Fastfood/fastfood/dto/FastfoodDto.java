package com.projects.Fastfood.fastfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FastfoodDto {

    private Long id;
    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;
}