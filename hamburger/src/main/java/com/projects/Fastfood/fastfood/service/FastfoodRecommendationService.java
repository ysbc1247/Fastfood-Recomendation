package com.projects.Fastfood.fastfood.service;

import com.projects.Fastfood.api.dto.DocumentDto;
import com.projects.Fastfood.api.dto.KakaoAPIResponseDto;
import com.projects.Fastfood.api.service.KakaoAddressSearchService;
import com.projects.Fastfood.direction.dto.OutputDto;
import com.projects.Fastfood.direction.entity.Direction;
import com.projects.Fastfood.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class FastfoodRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDto> recommendFastfoodList(String address) {
        KakaoAPIResponseDto kakaoAPIResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoAPIResponseDto) || CollectionUtils.isEmpty(kakaoAPIResponseDto.getDocumentList())) {
            log.error("Input address {} is not valid/null", address);
            return Collections.emptyList();
        }
        DocumentDto documentDto = kakaoAPIResponseDto.getDocumentList().get(0);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto convertToOutputDto(Direction direction) {
        return OutputDto.builder()
                .storeName(direction.getTargetStoreName())
                .storeAddress(direction.getTargetAddress())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}