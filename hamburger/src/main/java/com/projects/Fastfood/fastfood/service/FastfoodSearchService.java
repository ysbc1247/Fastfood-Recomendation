package com.projects.Fastfood.fastfood.service;

import com.projects.Fastfood.fastfood.dto.FastfoodDto;
import com.projects.Fastfood.fastfood.entity.Fastfood;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class FastfoodSearchService {
    private final FastfoodRepositoryService fastfoodRepositoryService;
    public List<FastfoodDto> searchFastfoodDtoList(){
        return fastfoodRepositoryService.findAll()
                .stream()
                .map(this::convertToFastfoodDto)
                .collect(Collectors.toList());
    }

    private FastfoodDto convertToFastfoodDto(Fastfood fastfood){
        return FastfoodDto.builder()
                .id(fastfood.getId())
                .storeName(fastfood.getStoreName())
                .storeAddress(fastfood.getStoreAddress())
                .latitude(fastfood.getLatitude())
                .longitude(fastfood.getLongitude())
                .build();
    }
}
