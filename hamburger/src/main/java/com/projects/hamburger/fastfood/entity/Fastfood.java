package com.projects.hamburger.fastfood.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "fastfood")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Fastfood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;
}
