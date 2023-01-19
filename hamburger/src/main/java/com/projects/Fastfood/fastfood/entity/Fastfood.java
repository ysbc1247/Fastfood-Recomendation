package com.projects.Fastfood.fastfood.entity;

import com.projects.Fastfood.BaseTimeEntity;
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

public class Fastfood extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;

    public void changeStoreAddress(String address){
        this.storeAddress = address;

    }
}
